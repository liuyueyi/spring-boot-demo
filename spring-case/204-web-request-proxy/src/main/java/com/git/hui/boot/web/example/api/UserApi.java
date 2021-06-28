package com.git.hui.boot.web.example.api;

import com.git.hui.boot.web.anno.Api;

/**
 * @author yihui
 * @date 2021/3/5
 */
@Api
public interface UserApi {

    String getName(int id);

    String updateName(String user, int age);

}
