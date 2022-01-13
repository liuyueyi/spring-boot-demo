package com.git.hui.boot.log4j.rmi;

import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

/**
 * @author yihui
 * @date 21/12/13
 */
public class HelloServiceV2 extends PortableRemoteObject implements HelloService {
    public HelloServiceV2() throws RemoteException {
        super();
    }

    @Override
    public String hello() throws RemoteException {
        System.out.println("process data! ");
        return "hello: " + LocalDateTime.now();
    }
}
