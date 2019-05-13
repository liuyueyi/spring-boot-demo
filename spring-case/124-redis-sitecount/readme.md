判断一个网站值不值钱的一个重要标准就是看pv/uv，那么你知道pv,uv是怎么统计的么？当然现在有第三方做的比较完善的可以直接使用，但如果让我们自己来实现这么一个功能，应该怎么做呢？

## I. 背景及需求

为了看看我的博客是不是我一个人的单机游戏，所以就想着统计一下总的访问量，每日的访问人数，哪些博文又是大家感兴趣的，点击得多的；

因此就萌发了自己撸一个pv/uv统计的服务，当然我这个也不需要特别完善高大上，能满足我自己的基本需要就可以了

- 希望统计站点(域名）总访问次数
- 希望统计站点总的访问人数，当前访问者在访问人数中的排名（即这个ip是所有访问ip中的第多少位访问的这个站点）
- 每个子页面都有访问次数，访问总人数，当前ip访问的排名统计
- 同一个ip，同一天内访问同一个子页面，pv次数只加1次；隔天之后，再次访问pv+1


## II. 方案设计

前面的背景和需求，可以说大致说明了我们要做个什么东西，以及需要注意哪些事项，再进行方案设计的过程中，则需要对需求进行详细拆解

### 1. 术语说明

前面提到了pv,uv，在我们的实际实现中，会发现这个服务中对于pv,uv的定义和标准定义并不是完全一致的，下面进行说明

#### a. pv

`page viste`, 每个页面的访问次数，在本服务中，我们的pv指的是总量，即从开始接入时，到现在总的访问次数

但是这里有个限制： **一个合法的ip，一天之内pv统计次数只能+1次**

- 根据ip进行区分，因此需要获取访问者ip
- 同一天内，这个ip访问相同的URI，只能算一次有效pv；第二天之后，再次访问，则可以再算一次有效pv

#### b. hot

前面的pv针对ip进行了限制，一个ip同一天的访问，只能计算一次，大部分情况下这种统计并没有什么问题，但是如果一个文章写得特别有参考意义，导致有人重复的看，仔细的看，换着花样的刷新看，这个时候统计下总的访问次数是不是也挺好的

因此在这个服务中，引入了hot（热度）的概念，对于一个uri而言，只要一次点击，hot+1

#### c. uv

`unique visitor`, 这个就是统计URI的访问ip数

### 2. 流程图

通过前面三个术语的定义，我们的操作流程就相对清晰了，我们的服务接收一个IP和URI，然后操作对应的pv,uv,hot并返回

- 首先判断这个ip是否为第一次访问这个URI
- 是，则pv+1, uv+1, hot+1
- 否，表示之前访问过，uv就不能变了
  - 判断是否今天第一次访问
  - 是，今天访问过，那么pv不变，hot+1
  - 否，之前访问过，今天没有，pv可以+1， hot+1
  
对应的流程图如下

![流程图](http://spring.hhui.top/spring-blog/imgs/190513/00.jpg)

### 3. 数据结构

流程清晰之后，接下来就需要看下pv,uv,hot三个数据怎么存了

#### a. pv

pv保存的就是访问次数，与ip无关，所以kv存储就可以满足我们的需求了，这里的key为uri，value则保存pv的值

#### b. hot

hot和pv类似，同样用kv可以满足要求

#### c. uv

uv这里有两个数据，一个是uv总数，要给是这个ip的访问排名，redis中有个zset数据结构正好就可以做这个

zset数据结构中，我们定义value为ip，score为ip的排名，那么uv就是最大的score了

#### d. 结构图

![流程图](http://spring.hhui.top/spring-blog/imgs/190513/01.jpg)

### 4. 方案设计

流程清晰，结构设计出来之后，就可以进入具体的方案设计环节了，在这个环节中，我们引入一个app的维度，这样我们的服务就可以通用了；

每个使用者都申请一个app，那么这个使用者的请求的所有站点统计数据，都关联到这个app上，这样也有利于后续统计了

#### a. 接口API

引入了app之后，结合前面的两个参数ip + URI，我们的请求参数就清晰了

```java
@Data
public class VisitReqDTO {
    /**
     * 应用区分
     */
    private String app;

    /**
     * 访问者ip
     */
    private String ip;

    /**
     * 访问的URI
     */
    private String uri;
}
```

然后我们返回的数据，pv + uv + rank + hot，所以返回的基础VO如下

```java
/**
 * Created by @author yihui in 16:19 19/5/12.
 */
@Data
@AllArgsConstructor
public class VisitVO implements Serializable {
    /**
     * pv，与传统的有点区别，这里表示这个url的总访问次数；每个ip，一天次数只+1
     */
    private Long pv;

    /**
     * uv 页面总的ip访问数
     */
    private Long uv;

    /**
     * 当前ip，第一次访问本url的排名
     */
    private Long rank;

    /**
     * 热度，每次访问计数都+1
     */
    private Long hot;

    public VisitVO() {
    }

    public VisitVO(VisitVO visitVO) {
        this.pv = visitVO.pv;
        this.uv = visitVO.uv;
        this.rank = visitVO.rank;
        this.hot = visitVO.hot;
    }
}
```

此外需要注意一点的是，发起一个子页面的请求时，这个时候我们基于域名的站点总数统计也应该被触发（简单来说，访问`http://spring.hhui.top/spring-blog/`时，不仅这个uri的统计需要更新， `spring.hhui.top`这个域名的pv,uv,hot也需要随之统计）

因此我们最终的返回对象应该是

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteVisitDTO {

    /**
     * 站点访问统计
     */
    private VisitVO siteVO;

    /**
     * 页面访问统计
     */
    private VisitVO uriVO;

}
```

有输出，又返回，那么访问api就简单了

```java
SiteVisitDTO visit(VisitReqDTO reqDTO);
```

#### b. hot相关api

hot数据结构为hash，每次请求过来，都是次数+1，因此直接使用redis的 `hIncrBy`，实现计数+1，并返回最终的计数

- key: `"hot_cnt_" + app` 作为hash的key
- field: 使用URI作为hash的field
- value: 保存具体的hot，整型

![hot api](http://spring.hhui.top/spring-blog/imgs/190513/02.jpg)

```java
/**
 * 应用的热度统计计数
 *
 * @param app
 * @return
 */
private String buildHotKey(String app) {
    return "hot_cnt_" + app;
}

/**
 * 热度，每访问一次，计数都+1
 *
 * @param key
 * @param uri
 * @return
 */
public Long addHot(String key, String uri);
```

#### c. pv相关api

pv与hot不一样的是并不是每次都需要计数+1，所以它需要有一个查询pv的接口，和一个计数+1的接口

- key: `"site_cnt_" + app` 作为hash的key
- field: 使用URI作为hash的field
- value: 保存具体的pv，整型

![pv api](http://spring.hhui.top/spring-blog/imgs/190513/03.jpg)

```java
/**
 * 应用的pv统计计数
 *
 * @param app
 * @return
 */
private String buildPvKey(String app) {
    return "site_cnt_" + app;
}

/**
 * 获取pv
 *
 * pv存储结果为hash，一个应用一个key; field 为uri； value为pv
 *
 * @return null表示首次有人访问；这个时候需要+1
 */
public Long getPv(String key, String uri);


/**
 * pv 次数+1
 *
 * @param key
 * @param uri
 */
public void addPv(String key, String uri) 
```

#### d. uv相关api

前面说到uv采用的是zset数据结构，其中ip作为value，排名作为score；所以uv就是最大的score

- key: 根据app和uri来确定uv的key
- value: 存储访问者ip（ipv4格式的）
- score: 排名，整型

![uv api](http://spring.hhui.top/spring-blog/imgs/190513/04.jpg)

因为uv需要返回两个结构，所以我们的返回需要注意

```java
/**
 * app+uri 对应的uv
 *
 * @param app
 * @param uri
 * @return
 */
private String buildUvKey(String app, String uri) {
    return "uri_rank_" + app + "_" + uri;
}


/**
 * 获取uri对应的uv，以及当前访问ip的历史访问排名
 * 使用zset来存储，key为uri唯一标识；value为ip；score为访问的排名
 *
 * @param key : 由app与URI来生成，即一个uri维护一个uv集
 * @param ip: 访问者ip
 * @return 返回uv/rank, 如果对应的值为0，表示没有访问过
 */
public ImmutablePair</** uv */Long, /** rank */Long> getUv(String key, String ip) 

/**
 * uv +1
 *
 * @param key
 * @param ip
 * @param rank
 */
public void addUv(String key, String ip, Long rank) 
```

#### e. 今日是否访问

前面的都还算比较简单，接下来有个非常有意思的地方了，如何判断这个ip，今天访问没访问？

**方案一**

要实现这个功能，一个自然而然的想法就出来了，直接kv就行了

- key: `uri_年月日_ip`
- value: 1

如果value存在，表示今天访问过，如果不存在，则没有访问过


**方案二**

前面那个倒是没啥问题，如果我希望统计今天某个uri的ip访问数，上面的就不太好处理，很容易想到用hash来替换

- key: `uri_年月日`
- field: `ip`
- value: 1

同样value存在，则表示今天访问过；否则没有访问过

如果需要统计今天访问的总数，hlen一把就可以；还可以获取今天所有访问过的ip

**方案三**

前面的方案看似挺好的，但是有个缺陷，如果我这个站点特别火，每天几百万的uv，这个存储量就有点夸张了

```
# 简单的算一下 10w uv的存储开销
field: ip   # 一个ip(255.255.255.255) 字符串存储算 16B；
value: 1  # 算 1B

10w uv = 10w * 17B = 1.7MB

# 假设这个站点有100个10w uv的子页面，每天存储需要 170MB
```

通过上面简单的计算可以看出这存储开销对于比较火的站点而言，有点吓人；然后可以找其他的存储方式了，所以bitmap可以隆重登场了

![bitmap](http://spring.hhui.top/spring-blog/imgs/190513/05.jpg)

我们将位数组分成四节，分别于ip的四段对应，因为ipv4每一段取值是(0-2^8)，所以我们的位数组，也只需要(4 * 8b = 4B)，相比较前面的方案来说，存储空间大大减少

看到上面这个结构，会有一个疑问，为什么分成四节？将ip转成整形，作为下标，一个就可以了

- 答：将ip转为整型，取值将是 (0 - 2^32)，需要的bitmap空间为`4Gb`，显然不如上面优雅

---

**方案确定**

上面三个方案中，我们选择了第三个，对应的api设计也比较简单了

```java

// 获取今天的日期，格式为 20190512
public static String getToday() {
    LocalDate date = LocalDate.now();
    int year = date.getYear();
    int month = date.getMonthValue();
    int day = date.getDayOfMonth();

    StringBuilder buf = new StringBuilder(8);
    return buf.append(year).append(month < 10 ? "0" : "").append(month).append(day < 10 ? "0" : "").append(day)
            .toString();
}
    
    
/**
 * 每日访问统计
 *
 * @param app
 * @param uri
 * @return
 */
private String buildUriTagKey(String app, String uri) {
    return "uri_tag_" + DateUtil.getToday() + "_" + app + "_" + uri;
}


/**
 * 标记ip访问过这个key
 *
 * @param key
 * @param ip
 */
public void tagVisit(String key, String ip)
```

## III. 服务实现

前面接口设计出来，按照既定思路实现就属于比较轻松的环节了

#### 1. pv接口实现

pv两个接口，一个访问，一个计数+1，都可以直接使用redisTemplate的基础操作完成

```java
/**
 * 获取pv
 *
 * pv存储结果为hash，一个应用一个key; field 为uri； value为pv
 *
 * @return null表示首次有人访问；这个时候需要+1
 */
public Long getPv(String key, String uri) {
    return redisTemplate.execute(new RedisCallback<Long>() {
        @Override
        public Long doInRedis(RedisConnection connection) throws DataAccessException {
            byte[] ans = connection.hGet(key.getBytes(), uri.getBytes());
            if (ans == null || ans.length == 0) {
                return null;
            }

            return Long.parseLong(new String(ans));
        }
    });
}

/**
 * pv 次数+1
 *
 * @param key
 * @param uri
 */
public void addPv(String key, String uri) {
    redisTemplate.execute(new RedisCallback<Void>() {
        @Override
        public Void doInRedis(RedisConnection connection) throws DataAccessException {
            connection.hIncrBy(key.getBytes(), uri.getBytes(), 1);
            return null;
        }
    });
}
```

### 2. hot接口实现

只有一个计数+1的接口

```java
/**
 * 热度，每访问一次，计数都+1
 *
 * @param key
 * @param uri
 * @return
 */
public Long addHot(String key, String uri) {
    return redisTemplate.execute(new RedisCallback<Long>() {
        @Override
        public Long doInRedis(RedisConnection connection) throws DataAccessException {
            return connection.hIncrBy(key.getBytes(), uri.getBytes(), 1);
        }
    });
}
```

### 3. uv接口实现

uv的获取会麻烦一点，首先获取uv值，然后获取ip对应的排名；如果uv为0，排名也就不需要再获取了

```java
/**
 * 获取uri对应的uv，以及当前访问ip的历史访问排名
 * 使用zset来存储，key为uri唯一标识；value为ip；score为访问的排名
 *
 * @param key : 由app与URI来生成，即一个uri维护一个uv集
 * @param ip: 访问者ip
 * @return 返回uv/rank, 如果对应的值为0，表示没有访问过
 */
public ImmutablePair</** uv */Long, /** rank */Long> getUv(String key, String ip) {
    // 获取总uv数，也就是最大的score
    Long uv = redisTemplate.execute(new RedisCallback<Long>() {
        @Override
        public Long doInRedis(RedisConnection connection) throws DataAccessException {
            Set<RedisZSetCommands.Tuple> set = connection.zRangeWithScores(key.getBytes(), -1, -1);
            if (CollectionUtils.isEmpty(set)) {
                return 0L;
            }

            Double score = set.stream().findFirst().get().getScore();
            return score.longValue();
        }
    });

    if (uv == null || uv == 0L) {
        // 表示还没有人访问过
        return ImmutablePair.of(0L, 0L);
    }

    // 获取ip对应的访问排名
    Long rank = redisTemplate.execute(new RedisCallback<Long>() {
        @Override
        public Long doInRedis(RedisConnection connection) throws DataAccessException {
            Double score = connection.zScore(key.getBytes(), ip.getBytes());
            return score == null ? 0L : score.longValue();
        }
    });

    return ImmutablePair.of(uv, rank);
}

/**
 * uv +1
 *
 * @param key
 * @param ip
 * @param rank
 */
public void addUv(String key, String ip, Long rank) {
    redisTemplate.execute(new RedisCallback<Void>() {
        @Override
        public Void doInRedis(RedisConnection connection) throws DataAccessException {
            connection.zAdd(key.getBytes(), rank, ip.getBytes());
            return null;
        }
    });
}
```


### 4. 今天是否访问过

前面选择位数组方式来记录是否访问过，这里的实现选择了简单的实现方式，利用四个bitmap来分别对应ip的四段；（实际上一个也可以实现，可以想一想应该怎么做）


```java
/**
 * 判断ip今天是否访问过
 * 采用bitset来判断ip是否有访问，key由app与uri唯一确定
 *
 * @return true 表示今天访问过/ false 表示今天没有访问过
 */
public boolean visitToday(String key, String ip) {
    // ip地址进行分段 127.0.0.1
    String[] segments = StringUtils.split(ip, ".");
    for (int i = 0; i < segments.length; i++) {
        if (!contain(key + "_" + i, Integer.valueOf(segments[i]))) {
            return false;
        }
    }
    return true;
}

private boolean contain(String key, Integer val) {
    return redisTemplate.execute(new RedisCallback<Boolean>() {
        @Override
        public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            return connection.getBit(key.getBytes(), val);
        }
    });
}


/**
 * 标记ip访问过这个key
 *
 * @param key
 * @param ip
 */
public void tagVisit(String key, String ip) {
    String[] segments = StringUtils.split(ip, ".");
    for (int i = 0; i < segments.length; i++) {
        int finalI = i;
        redisTemplate.execute(new RedisCallback<Void>() {
            @Override
            public Void doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setBit((key + "_" + finalI).getBytes(), Integer.valueOf(segments[finalI]), true);
                return null;
            }
        });

    }
}
```

### 4. api接口实现

前面基本的接口实现之后，api就是流程图的翻译了，也没有什么特别值得说到的地方，唯一需要注意的就是URI的解析，域名作为站点；uri由path + segment构成

```java
public static ImmutablePair</**host*/String, /**uri*/String> foramtUri(String uri) {
    URI u = URI.create(uri);
    String host = u.getHost();
    if (u.getPort() > 0 && u.getPort() != 80) {
        host = host + ":80";
    }

    String baseUri = u.getPath();
    if (u.getFragment() != null) {
        baseUri = baseUri + "#" + u.getFragment();
    }

    if (StringUtils.isNotBlank(baseUri)) {
        baseUri = host + baseUri;
    } else {
        baseUri = host;
    }

    return ImmutablePair.of(host, baseUri);
}
    
/**
 * uri 访问统计
 *
 * @param reqDTO
 * @return
 */
public SiteVisitDTO visit(VisitReqDTO reqDTO) {
    ImmutablePair<String, String> uri = URIUtil.foramtUri(reqDTO.getUri());

    // 获取站点的访问记录
    VisitVO uriVisit = doVisit(reqDTO.getApp(), uri.getRight(), reqDTO.getIp());
    VisitVO siteVisit;
    if (uri.getLeft().equals(uri.getRight())) {
        siteVisit = new VisitVO(uriVisit);
    } else {
        siteVisit = doVisit(reqDTO.getApp(), uri.getLeft(), reqDTO.getIp());
    }

    return new SiteVisitDTO(siteVisit, uriVisit);
}

private VisitVO doVisit(String app, String uri, String ip) {
    String pvKey = buildPvKey(app);
    String hotKey = buildHotKey(app);
    String uvKey = buildUvKey(app, uri);
    String todayVisitKey = buildUriTagKey(app, uri);

    Long hot = visitService.addHot(hotKey, uri);

    // 获取pv数据
    Long pv = visitService.getPv(pvKey, uri);
    if (pv == null || pv == 0) {
        // 历史没有访问过，则pv + 1, uv +1
        visitService.addPv(pvKey, uri);
        visitService.addUv(uvKey, ip, 1L);
        visitService.tagVisit(todayVisitKey, ip);
        return new VisitVO(1L, 1L, 1L, hot);
    }


    // 判断ip今天是否访问过
    boolean visit = visitService.visitToday(todayVisitKey, ip);

    // 获取uv及排名
    ImmutablePair</**uv*/Long, /**rank*/Long> uv = visitService.getUv(uvKey, ip);

    if (visit) {
        // 今天访问过，则不需要修改pv/uv；可以直接返回所需数据
        return new VisitVO(pv, uv.getLeft(), uv.getRight(), hot);
    }

    // 今天没访问过
    if (uv.left == 0L) {
        // 首次有人访问, pv + 1; uv +1
        visitService.addPv(pvKey, uri);
        visitService.addUv(uvKey, ip, 1L);
        visitService.tagVisit(todayVisitKey, ip);
        return new VisitVO(pv + 1, 1L, 1L, hot);
    } else if (uv.right == 0L) {
        // 这个ip首次访问, pv +1; uv + 1
        visitService.addPv(pvKey, uri);
        visitService.addUv(uvKey, ip, uv.left + 1);
        visitService.tagVisit(todayVisitKey, ip);
        return new VisitVO(pv + 1, uv.left + 1, uv.left + 1, hot);
    } else {
        // 这个ip的今天第一次访问， pv + 1 ; uv 不变
        visitService.addPv(pvKey, uri);
        visitService.tagVisit(todayVisitKey, ip);
        return new VisitVO(pv + 1, uv.left, uv.right, hot);
    }
}
```

## IV. 测试与小结

### 1. 测试

搭建一个简单的web服务，开始测试

```java
/**
 * Created by @author yihui in 18:58 19/5/12.
 */
@Controller
public class VisitController {
    @Autowired
    private SiteVisitFacade siteVisitFacade;

    @RequestMapping(path = "visit")
    @ResponseBody
    public SiteVisitDTO visit(VisitReqDTO reqDTO) {
        return siteVisitFacade.visit(reqDTO);
    }
}
```

#### a. 首次访问

```bash
# 首次访问，返回的全是1
http://localhost:8080/visit?app=demo&ip=192.168.0.1&uri=http://hhui.top/home
```

![test a](http://spring.hhui.top/spring-blog/imgs/190513/06.jpg)

#### b. 再次访问

```bash
# 再次访问，因为同样是今天访问，除了hot为2；其他的都是1
http://localhost:8080/visit?app=demo&ip=192.168.0.1&uri=http://hhui.top/home
```
![test b](http://spring.hhui.top/spring-blog/imgs/190513/07.jpg)

#### c. 同ip，不同URI

```bash
# 同一ip，换个uri；除站点返回hot为3，其他的全是1
http://localhost:8080/visit?app=demo&ip=192.168.0.1&uri=http://hhui.top/index
```
![test c](http://spring.hhui.top/spring-blog/imgs/190513/08.jpg)

#### d. 不同ip，接上一个URI

```bash
# 换个ip，这个uri；主站点hot=4, pv,uv,rank=2; uriVO全是2
http://localhost:8080/visit?app=demo&ip=192.168.0.2&uri=http://hhui.top/index
```

![test d](http://spring.hhui.top/spring-blog/imgs/190513/09.jpg)


#### e. 上一个ip，换第一个uri

```bash
# 换个ip，这个uri；主站点hot=5, pv,uv,rank=2; uriVO hot为3，其他全是2
http://localhost:8080/visit?app=demo&ip=192.168.0.2&uri=http://hhui.top/home
```

![test e](http://spring.hhui.top/spring-blog/imgs/190513/10.jpg)

#### f. 第二天访问

真要第二天操作有点麻烦，为了验证，直接干掉今天的占位标记

![rest](http://spring.hhui.top/spring-blog/imgs/190513/11.jpg)

```bash
# 模拟第二天访问， pv + 1， uv不变， hot+1
http://localhost:8080/visit?app=demo&ip=192.168.0.2&uri=http://hhui.top/home
```

![test f](http://spring.hhui.top/spring-blog/imgs/190513/12.jpg)

### 2. 小结

本文可以说是redis学习之后，一个挺好的应用场景，涉及到了我们常用和不常用的几个数据结构，包括hash,zset,bitmap, 其中关于bitmap的使用个人感觉还是非常有意思的；

对于redis操作不太熟的，可以参考下前面几篇博文

- [181029-SpringBoot高级篇Redis之基本配置](http://spring.hhui.top/spring-blog/2018/10/29/181029-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8B%E5%9F%BA%E6%9C%AC%E9%85%8D%E7%BD%AE/)
- [181101-SpringBoot高级篇Redis之Jedis配置](http://spring.hhui.top/spring-blog/2018/11/01/181101-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BJedis%E9%85%8D%E7%BD%AE/)
- [181108-SpringBoot高级篇Redis之String数据结构的读写](http://spring.hhui.top/spring-blog/2018/11/08/181108-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BString%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E7%9A%84%E8%AF%BB%E5%86%99/)
- [181109-SpringBoot高级篇Redis之List数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/11/09/181109-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BList%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181202-SpringBoot高级篇Redis之Hash数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/02/181202-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BHash%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181211-SpringBoot高级篇Redis之Set数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/11/181211-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BSet%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181212-SpringBoot高级篇Redis之ZSet数据结构使用姿势](http://spring.hhui.top/spring-blog/2018/12/12/181212-SpringBoot%E9%AB%98%E7%BA%A7%E7%AF%87Redis%E4%B9%8BZSet%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%BD%BF%E7%94%A8%E5%A7%BF%E5%8A%BF/)
- [181225-SpringBoot应用篇之借助Redis实现排行榜功能](http://spring.hhui.top/spring-blog/2018/12/25/181225-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4%B9%8B%E5%80%9F%E5%8A%A9Redis%E5%AE%9E%E7%8E%B0%E6%8E%92%E8%A1%8C%E6%A6%9C%E5%8A%9F%E8%83%BD/)


**注意**

上面这个服务，在实际使用中，需要考虑并发问题，很明显我们上的设计并不是多线程安全的，也就是说，在并发量大的时候，获取的数据极有可能和预期的不一致

**扩展**

上文的设计中，每个uri都有一组位图，我们可以通过遍历，获取value为1的下标，来统计这个页面今天的pv数，以及更相信的今天哪些ip访问过；同样也可以分析站点的今日UV数，以及对应的访问ip


### 0. 项目

- 工程：[spring-boot-demo](https://github.com/liuyueyi/spring-boot-demo)

### 1. 一灰灰Blog

- 一灰灰Blog个人博客 [https://blog.hhui.top](https://blog.hhui.top)
- 一灰灰Blog-Spring专题博客 [http://spring.hhui.top](http://spring.hhui.top)

一灰灰的个人博客，记录所有学习和工作中的博文，欢迎大家前去逛逛


### 2. 声明

尽信书则不如，以上内容，纯属一家之言，因个人能力有限，难免有疏漏和错误之处，如发现bug或者有更好的建议，欢迎批评指正，不吝感激

- 微博地址: [小灰灰Blog](https://weibo.com/p/1005052169825577/home)
- QQ： 一灰灰/3302797840

### 3. 扫描关注

**一灰灰blog**

![QrCode](https://raw.githubusercontent.com/liuyueyi/Source/master/img/info/blogInfoV2.png)

**知识星球**

![goals](https://raw.githubusercontent.com/liuyueyi/Source/master/img/info/goals.png)

