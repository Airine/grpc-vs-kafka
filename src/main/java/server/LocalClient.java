package server;

import java.util.ArrayList;

public class LocalClient implements TestClient {
    ArrayList<Integer> ones;
    ArrayList<Integer> twos;

    public LocalClient() {
        ones = new ArrayList<>();
        twos = new ArrayList<>();
    }

    public String requestOne(int i) {
        ones.add(i);
        return "Hello Word, " + ones.size();
    }

    public String requestTwo(int i) {
        twos.add(i);
        return "Bye Word, " + twos.size();
    }
}
