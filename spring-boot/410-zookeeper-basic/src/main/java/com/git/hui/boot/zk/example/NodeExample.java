package com.git.hui.boot.zk.example;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 节点查询，创建，获取
 *
 * @author yihui
 * @date 2021/4/14
 */
@Service
public class NodeExample implements Watcher {

    private ZooKeeper zooKeeper;

    @PostConstruct
    public void initZk() throws IOException {
        zooKeeper = new ZooKeeper("127.0.0.1:2181", 500_000, this);
    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("zk event: " + event);
    }


    public void test() {
        String root = "/zk-test";
        nodeCreate(root);
        nodeChildren(root);
        checkPathExist(root);
        dataChange(root + "/yes");
        watchEvent(root);
        deleteNode(root + "/yes");
    }


    /**
     * 创建节点
     *
     * @param path
     */
    private void nodeCreate(String path) {
        // 第三个参数ACL 表示访问控制权限
        // 第四个参数，控制创建的是持久节点，持久顺序节点，还是临时节点；临时顺序节点
        // 返回 the actual path of the created node
        // 单节点存在时，抛异常 KeeperException.NodeExists
        try {
            String node = zooKeeper.create(path + "/yes", "保存的数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("create node: " + node);
        } catch (KeeperException.NodeExistsException e) {
            // 节点存在
            System.out.println("节点已存在: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 带生命周期的节点
        try {
            Stat stat = new Stat();
            // 当这个节点上没有child，且1s内没有变动，则删除节点
            // 实测抛了异常，未知原因
            String node = zooKeeper.create(path + "/ttl", ("now: " + LocalDateTime.now()).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_WITH_TTL, stat, 1000);
            System.out.println("ttl nod:" + node + " | " + stat);
            // 创建已给监听器来验证
            zooKeeper.exists(path + "/ttl", (e) -> {
                System.out.println("ttl 节点变更: " + e);
            });
        } catch (KeeperException.NodeExistsException e) {
            System.out.println("节点已存在: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取节点的所有子节点, 只能获取一级节点
     *
     * @param path
     */
    private void nodeChildren(String path) {
        try {
            // 如果获取成功，会监听 当前节点的删除，子节点的创建和删除，触发回调事件, 这个回调也只会触发一次
            List<String> children = zooKeeper.getChildren(path, this, new Stat());
            System.out.println("path:" + path + " 's children:" + children);
        } catch (KeeperException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断节点是否存在
     */
    private void checkPathExist(String path) {
        try {
            // 节点存在，则返回stat对象； 不存在时，返回null
            // watch: true 表示给这个节点添加监听器，当节点出现创建/删除 或者 新增数据时，触发watcher回调
            Stat stat = zooKeeper.exists(path + "/no", false);
            System.out.println("NoStat: " + stat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 判断节点是否存在，并监听 节点的创建 + 删除 + 数据变更
            // 注意这个事件监听，只会触发一次，即单这个节点数据变更多次，只有第一次能拿到，之后的变动，需要重新再注册监听
            Stat stat = zooKeeper.exists(path + "/yes", this);
            System.out.println("YesStat: " + stat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置数据，获取数据
     *
     * @param path
     */
    public void dataChange(String path) {
        try {
            Stat stat = new Stat();
            byte[] data = zooKeeper.getData(path, false, stat);
            System.out.println("path: " + path + " data: " + new String(data) + " : " + stat);

            // 根据版本精确匹配; version = -1 就不需要进行版本匹配了
            Stat newStat = zooKeeper.setData(path, ("new data" + LocalDateTime.now()).getBytes(), stat.getVersion());
            System.out.println("newStat: " + stat.getVersion() + "/" + newStat.getVersion() + " data: " + new String(zooKeeper.getData(path, false, stat)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void watchEvent(String path) {
        try {
            // 注意这个节点存在
            // 添加监听, 与 exist 不同的在于，触发之后，依然有效还会被触发， 只有手动调用remove才会取消
            // 感知： 节点创建，删除，数据变更 ； 创建子节点，删除子节点
            // 无法感知： 子节点的字节点创建/删除， 子节点的数据变更
            zooKeeper.addWatch(path + "/yes", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("事件触发 on " + path + " event:" + event);
                }
            }, AddWatchMode.PERSISTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 注意这个节点不存在
            // 添加监听, 与 exist 不同的在于，触发之后，依然有效还会被触发， 只有手动调用remove才会取消
            // 与前面的区别在于，它的子节点的变动也会被监听到
            zooKeeper.addWatch(path + "/no", new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("事件触发 on " + path + " event:" + event);
                }
            }, AddWatchMode.PERSISTENT_RECURSIVE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 移除所有的监听
        //zooKeeper.removeAllWatches(path, WatcherType.Any, true);
    }

    /**
     * 删除节点
     */
    public void deleteNode(String path) {
        try {
            // 根据版本限定删除， -1 表示删除所有；否则需要版本匹配，不然就会抛异常
            zooKeeper.delete(path, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
