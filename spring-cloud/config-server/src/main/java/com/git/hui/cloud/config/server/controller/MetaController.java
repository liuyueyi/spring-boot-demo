package com.git.hui.cloud.config.server.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.hsf.HSFJSONUtils;
import com.git.hui.cloud.config.server.modal.ResWrapper;
import com.git.hui.cloud.config.server.modal.data.MetaData;
import com.git.hui.cloud.config.server.modal.req.ConfReqDO;
import com.git.hui.cloud.config.server.modal.req.GroupAddReqDO;
import com.git.hui.cloud.config.server.modal.req.IndexReqDO;
import com.git.hui.cloud.config.server.modal.res.ConfVO;
import com.git.hui.cloud.config.server.modal.res.DetailResVO;
import com.git.hui.cloud.config.server.modal.res.IndexResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * fixme 目前配置修改没有进行安全防护，因此是由SQL注入漏洞的
 *
 * Created by @author yihui in 11:18 18/9/5.
 */
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/meta")
public class MetaController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(path = {"/apps"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper<String> getClients() {
        List<String> result = discoveryClient.getServices();
        return ResWrapper.buildSuccess(JSONObject.toJSONString(result));
    }
    
    /**
     * 应用配置首页
     *
     * @param indexReq
     * @return
     */
    @RequestMapping(path = {"/", "/index", "/index/"},
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper<List<IndexResVO>> index(IndexReqDO indexReq) {
        List<IndexResVO> list = new ArrayList<>(MetaData.indexMap.values());
        list.sort((o1, o2) -> (int) (o2.getDate() - o1.getDate()));
        return ResWrapper.buildSuccess(list);
    }

    /**
     * 应用配置详情页
     *
     * @param groupId
     * @return
     */
    @RequestMapping(path = {"/detail"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper<DetailResVO> list(@RequestParam Long groupId) {
        List<Long> confIds = MetaData.groupConfMap.getOrDefault(groupId, new ArrayList<>());

        IndexResVO index = MetaData.indexMap.get(groupId);
        DetailResVO detailResVO = new DetailResVO();
        detailResVO.setApplication(index.getApplication());
        detailResVO.setGroupId(groupId);
        detailResVO.setLabel(index.getLabel());
        detailResVO.setProfile(index.getProfile());

        List<ConfVO> confList = new ArrayList<>();
        for (long id : confIds) {
            confList.add(MetaData.confMap.get(id));
        }
        detailResVO.setConfList(confList);
        confList.sort((o1, o2) -> (int) (o2.getUpdate() - o1.getUpdate()));

        return ResWrapper.buildSuccess(detailResVO);
    }

    /**
     * 新增一个应用配置
     *
     * @param groupAddReqDO
     * @return
     */
    @RequestMapping(path = "/group/add", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper groupAdd(@RequestBody GroupAddReqDO groupAddReqDO) {
        IndexResVO index = new IndexResVO();
        index.setApplication(groupAddReqDO.getApplication());
        index.setUser("user");
        index.setDate(System.currentTimeMillis() / 1000);
        index.setProfile(groupAddReqDO.getProfile());
        index.setLabel(groupAddReqDO.getLabel());
        index.setGroupId(MetaData.getMaxGroupId());

        MetaData.indexMap.put(index.getGroupId(), index);
        return ResWrapper.buildSuccess(true);
    }


    @RequestMapping(path = "/group/del", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper groupDel(@RequestParam Long groupId) {
        // 先只删除实际的配置属性；而不删除整个配置文件，对于后续的备份恢复会有用

        MetaData.indexMap.remove(groupId);
        return ResWrapper.buildSuccess(true);
    }

    /**
     * 新增配置
     *
     * @param reqDO
     * @return
     */
    @RequestMapping(path = "/conf/add", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper confAdd(@RequestBody ConfReqDO reqDO) {
        ConfVO confVO = new ConfVO();
        confVO.setKey(reqDO.getKey());
        confVO.setValue(reqDO.getValue());
        confVO.setCreate(System.currentTimeMillis() / 1000);
        confVO.setUpdate(System.currentTimeMillis() / 1000);
        confVO.setConfId(MetaData.getMaxConfId());

        MetaData.confMap.put(confVO.getConfId(), confVO);

        List<Long> ids;
        if (MetaData.groupConfMap.get(reqDO.getGroupId()) == null) {
            ids = new ArrayList<>();
            ids.add(confVO.getConfId());
            MetaData.groupConfMap.put(reqDO.getGroupId(), ids);
        } else {
            MetaData.groupConfMap.get(reqDO.getGroupId()).add(confVO.getConfId());
        }

        return ResWrapper.buildSuccess(true);
    }

    /**
     * 修改配置
     *
     * @return
     */
    @RequestMapping(path = "/conf/edit", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper conEdit(@RequestBody ConfReqDO reqDO) {
        ConfVO confVO = MetaData.confMap.get(reqDO.getConfId());
        confVO.setKey(reqDO.getKey());
        confVO.setValue(reqDO.getValue());
        confVO.setUpdate(System.currentTimeMillis() / 1000);
        return ResWrapper.buildSuccess(confVO);
    }

    /**
     * 配置删除
     *
     * @return
     */
    @RequestMapping(path = "/conf/del", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public ResWrapper confDelete(ConfReqDO reqDO) {
        MetaData.confMap.remove(reqDO.getConfId());
        MetaData.groupConfMap.getOrDefault(reqDO.getGroupId(), Collections.emptyList()).remove(reqDO.getConfId());
        return ResWrapper.buildSuccess(true);
    }
}