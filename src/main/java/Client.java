import client.LocalClient;
import grpc.GrpcClient;
import kafka.KafkaClient;
import client.TestClient;
import rmi.RmiClient;

public class Client {
    static int N = 10000;
    public static void main(String[] args) {
        TestClient client =
//                new LocalClient();
//                new KafkaClient();
//                new GrpcClient();
                new RmiClient();

        if (client instanceof KafkaClient) {
            N = 10;
        }

        long start, end, avg = 0;
        start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            if (i % 2 != 0) {
                System.out.println(client.requestOne(i));
            } else {
                System.out.println(client.requestTwo(i));
            }
        }
        end = System.currentTimeMillis();
        avg += end - start;
        System.out.println("Total runtime = " + avg + " ms");
        System.out.println("Average latency = " + (double)avg/(double)N + " ms");
        if (client instanceof GrpcClient) {
            ((GrpcClient) client).shutDown();
        }
    }
}
