import kafka.KafkaClient;
import server.LocalClient;
import server.TestClient;

public class Client {
    static int N = 1000000;
    public static void main(String[] args) {
        TestClient client =
                new KafkaClient();
//                new LocalClient();

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
    }
}
