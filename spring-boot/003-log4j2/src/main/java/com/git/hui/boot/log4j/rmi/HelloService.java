package com.git.hui.boot.log4j.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author yihui
 * @date 21/12/13
 */
public interface HelloService extends Remote {

    String hello() throws RemoteException;

}
