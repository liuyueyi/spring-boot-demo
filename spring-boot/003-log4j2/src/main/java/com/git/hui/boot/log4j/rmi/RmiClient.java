package com.git.hui.boot.log4j.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

/**
 * @author yihui
 * @date 21/12/13
 */
public class RmiClient {
    public static void basic() throws Exception {
        Registry registry = LocateRegistry.getRegistry(8181);
        HelloService hello = (HelloService) registry.lookup("hello");
        System.out.println(hello.hello());
    }

    public static void naming() throws Exception {
        String remoteAddr = "rmi://localhost:8181/hello";
        HelloService hello = (HelloService) Naming.lookup(remoteAddr);
        String response = hello.hello();
        System.out.println(response);
    }

    public static void injectTest() throws Exception {
        // 使用JDNI在命名服务中发布引用
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
        env.put(Context.PROVIDER_URL, "rmi://127.0.0.1:8181");
        InitialContext context = new InitialContext(env);
        Object obj = context.lookup("rmi://127.0.0.1:8181/inject");
        System.out.println(obj);
    }

    public static void main(String[] args) throws Exception {
        injectTest();
    }
}
