package com.git.hui.cloud.api.eurka.api;

import com.git.hui.cloud.api.eurka.dto.UserDTO;

/**
 * Created by @author yihui in 08:07 18/9/2.
 */
public interface UserService {
    UserDTO getUserById(long userId);
}
