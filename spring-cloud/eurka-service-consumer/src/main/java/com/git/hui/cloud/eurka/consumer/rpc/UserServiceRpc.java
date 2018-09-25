package com.git.hui.cloud.eurka.consumer.rpc;

import com.git.hui.cloud.api.eurka.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by @author yihui in 08:15 18/9/2.
 */
@Service
@FeignClient(value = "eurka-service-provider/userService")
public interface UserServiceRpc {
    @GetMapping(path = "getUserById")
    UserDTO getUserById(@RequestParam("userId") long userId);
}
