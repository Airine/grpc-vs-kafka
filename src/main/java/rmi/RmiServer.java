package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RmiServer implements RmiServerStub {

    static Logger logger = LoggerFactory.getLogger(RmiServer.class);
    static int PORT = 5000;

    private final ArrayList<Integer> ones = new ArrayList<>();
    private final ArrayList<Integer> twos = new ArrayList<>();

    @Override
    public String requestOne(int i) throws RemoteException {
        ones.add(i);
        return "Hello World, " + ones.size();
    }

    @Override
    public String requestTwo(int i) throws RemoteException {
        twos.add(i);
        return "Bye World, " + twos.size();
    }

    public static void main(String[] args) {
        try {
            RmiServer rmiServer = new RmiServer();

            RmiServerStub stub = (RmiServerStub) UnicastRemoteObject.exportObject(rmiServer, PORT);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);
            System.out.println("RMI registered Server successfully.");
            System.out.println("Server ready.");
        } catch (Exception e) {
            logger.error("Server exception: {}", e.toString(), e);
            e.printStackTrace();
        }
    }
}
