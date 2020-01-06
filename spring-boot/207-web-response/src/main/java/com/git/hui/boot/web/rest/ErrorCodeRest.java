package com.git.hui.boot.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by @author yihui in 16:48 20/1/4.
 */
@RestController
@RequestMapping(path = "code")
public class ErrorCodeRest {

    /**
     * 注解方式，只支持标准http状态码
     *
     * @return
     */
    @GetMapping("ano")
    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "请求参数异常!")
    public String ano() {
        return "{\"code\": 400, \"msg\": \"bad request!\"}";
    }

    /**
     * 异常类 + 注解方式，只支持标准http状态码
     *
     * @return
     */
    @GetMapping("exception/500")
    public String serverException() {
        throw new ServerException("内部异常哦");
    }

    @GetMapping("exception/400")
    public String clientException() {
        throw new ClientException("客户端异常哦");
    }

    @GetMapping("401")
    public ResponseEntity<String> _401() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"code\": 401, \"msg\": \"未授权!\"}");
    }

    @GetMapping("451")
    public ResponseEntity<String> _451() {
        return ResponseEntity.status(451).body("{\"code\": 451, \"msg\": \"自定义异常!\"}");
    }

    /**
     * send error 方式，只支持标准http状态码; 且不会带上返回的结果
     *
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("410")
    public String _410(HttpServletResponse response) throws IOException {
        response.sendError(410, "send 410");
        return "{\"code\": 410, \"msg\": \"Gone 410!\"}";
    }

    @GetMapping("460")
    public String _460(HttpServletResponse response) throws IOException {
        response.sendError(460, "send 460");
        return "{\"code\": 460, \"msg\": \"Gone 460!\"}";
    }

    /**
     * response.setStatus 支持自定义http code，并可以返回结果
     *
     * @param response
     * @return
     */
    @GetMapping("525")
    public String _525(HttpServletResponse response) {
        response.setStatus(525);
        return "{\"code\": 525, \"msg\": \"自定义错误码 525!\"}";
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "服务器失联了，请到月球上呼叫试试~~")
    public static class ServerException extends RuntimeException {
        public ServerException(String message) {
            super(message);
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "老哥，你的请求有问题~~")
    public static class ClientException extends RuntimeException {
        public ClientException(String message) {
            super(message);
        }
    }
}
