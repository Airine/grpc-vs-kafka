package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerStub extends Remote {
    String requestOne(int i) throws RemoteException;
    String requestTwo(int i) throws RemoteException;
}
