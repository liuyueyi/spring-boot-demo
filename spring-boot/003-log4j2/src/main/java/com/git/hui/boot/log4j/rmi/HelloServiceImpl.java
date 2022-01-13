package com.git.hui.boot.log4j.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

/**
 * @author yihui
 * @date 21/12/13
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    public HelloServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String hello() throws RemoteException {
        System.out.println("process data! ");
        return "hello: " + LocalDateTime.now();
    }
}
