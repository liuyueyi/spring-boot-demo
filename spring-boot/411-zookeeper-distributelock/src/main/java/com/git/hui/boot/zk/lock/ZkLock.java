package com.git.hui.boot.zk.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * zk 分布式锁：
 * - 固定节点
 * - 请求锁时，从这个固定节点上，创建一个有序的临时节点，如果顺序最小，则表示获取到了锁；否则监听傻上一个临时节点的删除事件
 *
 * @author yihui
 * @date 2021/4/12
 */
public class ZkLock implements Watcher {

    private ZooKeeper zooKeeper;
    private String root;

    public ZkLock(String root) throws IOException {
        try {
            this.root = root;
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 500_000, this);
            Stat stat = zooKeeper.exists(root, false);
            if (stat == null) {
                // 不存在则创建
                createNode(root, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String createNode(String path, boolean persistent) throws Exception {
        return zooKeeper.create(path, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, persistent ? CreateMode.PERSISTENT : CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
        if (pre == null) {
            return;
        }
        synchronized (pre) {
            pre.notify();
        }
    }

    /**
     * 当前节点
     */
    private String current;

    /**
     * 前一个节点
     */
    private String pre;

    public boolean lock() {
        if (tryLock()) {
            return true;
        }

        try {
            // 监听前一个节点的删除事件
            Stat state = zooKeeper.exists(pre, true);
            if (state != null) {
                synchronized (pre) {
                    // 阻塞等待前面的节点释放
                    pre.wait();
                    // 这里不直接返回true，因为前面的一个节点删除，可能并不是因为它持有锁并释放锁，如果是因为这个会话中断导致临时节点删除，这个时候需要做的是换一下监听的 preNode
                    return lock();
                }
            } else {
                return lock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 尝试获取锁，创建顺序临时节点，若数据最小，则表示抢占锁成功；否则失败
     *
     * @return
     */
    public boolean tryLock() {
        try {
            String path = root + "/";
            if (current == null) {
                // 创建临时顺序节点
                current = createNode(path, false);
            }
            List<String> list = zooKeeper.getChildren(root, false);
            Collections.sort(list);

            if (current.equalsIgnoreCase(path + list.get(0))) {
                // 获取锁成功
                return true;
            } else {
                // 获取锁失败，找到前一个节点
                int index = Collections.binarySearch(list, current.substring(path.length()));
                // 查询当前节点前面的那个
                pre = path + list.get(index - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void unlock() {
        try {
            zooKeeper.delete(current, -1);
            current = null;
            zooKeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
