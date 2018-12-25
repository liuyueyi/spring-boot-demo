package com.git.hui.boot.redis.rank.component;

import com.git.hui.boot.redis.rank.modal.RankDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 排行榜组件
 * Created by @author yihui in 19:51 18/12/18.
 */
@Component
public class RankListComponent {
    @Autowired
    private RedisComponent redisComponent;

    private static final String RANK_PREFIX = "global_rank";

    private List<RankDO> buildRedisRankToBizDO(Set<ZSetOperations.TypedTuple<String>> result, long offset) {
        List<RankDO> rankList = new ArrayList<>(result.size());
        long rank = offset;
        for (ZSetOperations.TypedTuple<String> sub : result) {
            rankList.add(new RankDO(rank++, Math.abs(sub.getScore().floatValue()), Long.parseLong(sub.getValue())));
        }
        return rankList;
    }

    /**
     * 获取前n名的排行榜数据
     *
     * @param n
     * @return
     */
    public List<RankDO> getTopNRanks(int n) {
        Set<ZSetOperations.TypedTuple<String>> result = redisComponent.rangeWithScore(RANK_PREFIX, 0, n - 1);
        return buildRedisRankToBizDO(result, 1);
    }


    /**
     * 获取用户所在排行榜的位置，以及排行榜中其前后n个用户的排行信息
     *
     * @param userId
     * @param n
     * @return
     */
    public List<RankDO> getRankAroundUser(Long userId, int n) {
        // 首先是获取用户对应的排名
        RankDO rank = getRank(userId);
        if (rank.getRank() <= 0) {
            // fixme 用户没有上榜时，不返回
            return Collections.emptyList();
        }

        // 因为实际的排名是从0开始的，所以查询周边排名时，需要将n-1
        Set<ZSetOperations.TypedTuple<String>> result =
                redisComponent.rangeWithScore(RANK_PREFIX, Math.max(0, rank.getRank() - n - 1), rank.getRank() + n - 1);
        return buildRedisRankToBizDO(result, rank.getRank() - n);
    }


    /**
     * 获取用户的排行榜位置
     *
     * @param userId
     * @return
     */
    public RankDO getRank(Long userId) {
        // 获取排行， 因为默认是0为开头，因此实际的排名需要+1
        Long rank = redisComponent.rank(RANK_PREFIX, String.valueOf(userId));
        if (rank == null) {
            // 没有排行时，直接返回一个默认的
            return new RankDO(-1L, 0F, userId);
        }

        // 获取积分
        Double score = redisComponent.score(RANK_PREFIX, String.valueOf(userId));
        return new RankDO(rank + 1, Math.abs(score.floatValue()), userId);
    }

    /**
     * 更新用户积分，并获取最新的个人所在排行榜信息
     *
     * @param userId
     * @param score
     * @return
     */
    public RankDO updateRank(Long userId, Float score) {
        // 因为zset默认积分小的在前面，所以我们对score进行取反，这样用户的积分越大，对应的score越小，排名越高
        redisComponent.add(RANK_PREFIX, String.valueOf(userId), -score);
        Long rank = redisComponent.rank(RANK_PREFIX, String.valueOf(userId));
        return new RankDO(rank + 1, score, userId);
    }

}
