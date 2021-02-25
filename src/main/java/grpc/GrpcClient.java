package grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import client.TestClient;

import java.util.concurrent.TimeUnit;

public class GrpcClient implements TestClient {

    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
    private final grpc.TestServerGrpc.TestServerBlockingStub stub = grpc.TestServerGrpc.newBlockingStub(channel);

    public void shutDown() {
        if (channel != null) {
            try {
                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String requestOne(int i) {
        try {
            grpc.Reply reply = stub
                    .requestOne(grpc.Request.newBuilder().setI(i).build());

            return reply.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String requestTwo(int i) {
        try {
            grpc.Reply reply = stub
                    .requestTwo(grpc.Request.newBuilder().setI(i).build());

            return reply.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
