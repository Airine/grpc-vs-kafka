package rmi;

import client.TestClient;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient implements TestClient {

    static Logger logger = LoggerFactory.getLogger(RmiClient.class);
    static String IP_ADDR = "127.0.0.1";
    private RmiServerStub serverStub;
    public RmiClient() {
        try {
            Registry registry = LocateRegistry.getRegistry(IP_ADDR);
            serverStub = (RmiServerStub) registry.lookup("Server");
            logger.info("Get Tracker from rmi registry.");
        } catch (Exception e) {
            logger.error("Rmi exception: {}", e.toString(), e);
        }
    }
    @Override
    public String requestOne(int i) {
        try {
            return serverStub.requestOne(i);
        } catch (RemoteException e) {
            return null;
        }
    }

    @Override
    public String requestTwo(int i) {
        try {
            return serverStub.requestTwo(i);
        } catch (RemoteException e) {
            return null;
        }
    }
}
