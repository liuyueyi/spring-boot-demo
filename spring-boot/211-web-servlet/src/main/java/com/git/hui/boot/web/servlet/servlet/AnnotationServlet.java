package com.git.hui.boot.web.servlet.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 使用注解的方式来定义并注册一个自定义Servlet
 * Created by @author yihui in 19:08 19/11/21.
 */
@WebServlet(urlPatterns = "/annotation")
public class AnnotationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        PrintWriter writer = resp.getWriter();
        writer.write("[AnnotationServlet] welcome " + name);
        writer.flush();
        writer.close();
    }

}
