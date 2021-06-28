package com.git.hub.boot.web.enhanced.example.api;

import com.git.hub.boot.web.enhanced.anno.RestService;

/**
 * @author yihui
 * @date 2021/3/9
 */
@RestService
public interface UserApi {

    /**
     * getName by id
     *
     * @param id
     * @return
     */
    String getName(int id);


    /**
     * update name
     *
     * @param user
     * @param age
     * @return
     */
    String updateName(String user, int age);
}
