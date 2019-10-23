package com.git.hui.web.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by @author yihui in 14:58 19/10/10.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public static String getThrowableStackInfo(Throwable e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new java.io.PrintWriter(buf, true));
        String msg = buf.toString();
        try {
            buf.close();
        } catch (Exception t) {
            return e.getMessage();
        }
        return msg;
    }

    @ResponseBody
    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleArithmetic(HttpServletRequest request, HttpServletResponse response, ArithmeticException e)
            throws IOException {
        log.info("divide conf!");
        return "divide 0: " + getThrowableStackInfo(e);
    }

    @ResponseBody
    @ExceptionHandler(value = ArrayIndexOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleArrayIndexOutBounds(HttpServletRequest request, HttpServletResponse response,
            ArrayIndexOutOfBoundsException e) throws IOException {
        log.info("array index out conf!");
        return "aryIndexOutOfBounds: " + getThrowableStackInfo(e);
    }


    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerError(NoHandlerFoundException e, HttpServletResponse response) {
        return "noHandlerFound: " + getThrowableStackInfo(e);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String handleException(HttpServletRequest request, Exception e) {
        log.info("some conf: {}", e);
        return "500: " + getThrowableStackInfo(e);
    }
}
