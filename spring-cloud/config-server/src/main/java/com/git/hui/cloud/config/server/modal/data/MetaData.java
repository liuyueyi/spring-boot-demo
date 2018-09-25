package com.git.hui.cloud.config.server.modal.data;

import com.git.hui.cloud.config.server.modal.res.ConfVO;
import com.git.hui.cloud.config.server.modal.res.IndexResVO;

import java.util.*;

/**
 * Created by @author yihui in 21:04 18/9/15.
 */
public class MetaData {

    public static Map<Long, IndexResVO> indexMap;
    public static Map<Long, ConfVO> confMap;
    public static Map<Long, List<Long>> groupConfMap;

    static {
        init();
    }

    public static void init() {
        indexMap = new HashMap<>(16);
        for (int i = 0; i < 16; i++) {
            indexMap.put((long) i, generateIndexRes(i, i % 3 == 0 ? "conf_" : ""));
        }

        confMap = new HashMap<>(86);
        for (int i = 0; i < 86; i++) {
            confMap.put((long) i, generateConfVO(i));
        }

        groupConfMap = new HashMap<>();
        List<Long> list;
        for (int i = 0; i < 86; i++) {
            long key = i % 16;
            list = groupConfMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                groupConfMap.put(key, list);
            }

            list.add((long) i);
        }
    }

    private static IndexResVO generateIndexRes(int id, String prefix) {
        IndexResVO indexResVO = new IndexResVO();
        indexResVO.setGroupId((long) id);
        indexResVO.setDate(System.currentTimeMillis() / 1000);
        indexResVO.setApplication(prefix + UUID.randomUUID() + "_" + id);
        indexResVO.setLabel("master");
        indexResVO.setProfile(((int) (Math.random() * 10)) % 2 == 0 ? "pro" : "dev");
        indexResVO.setUser("user");
        return indexResVO;
    }

    private static ConfVO generateConfVO(int id) {
        ConfVO confVO = new ConfVO();
        confVO.setConfId((long) id);
        confVO.setCreate(System.currentTimeMillis() / 1000);
        confVO.setUpdate(System.currentTimeMillis() / 1000);
        confVO.setKey(UUID.randomUUID() + "_key");
        confVO.setValue(UUID.randomUUID() + "_value");
        return confVO;
    }

    public static long getMaxGroupId() {
        long max = 0;
        for (long key : indexMap.keySet()) {
            max = Math.max(key, max);
        }
        return max + 1;
    }

    public static long getMaxConfId() {
        long max = 0;
        for (long key : confMap.keySet()) {
            max = Math.max(key, max);
        }
        return max + 1;
    }
}
