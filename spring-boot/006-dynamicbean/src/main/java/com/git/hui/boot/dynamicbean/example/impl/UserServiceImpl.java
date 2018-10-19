package com.git.hui.boot.dynamicbean.example.impl;

import com.git.hui.boot.dynamicbean.example.api.IUserService;
import org.springframework.stereotype.Service;

/**
 * Created by @author yihui in 17:05 18/10/16.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    @Override
    public Integer getUserId(String uname) {
        return 1;
    }
}
