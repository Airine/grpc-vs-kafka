package grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;

public class GrpcServer {
    public static void main(String[] args) {
        try {

            int port = 50051;
            final Server server = ServerBuilder.forPort(port)
                    .addService(new TestServerImpl())
                    .build()
                    .start();
            System.out.println("Server started, listening on " + port);
            server.awaitTermination();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class TestServerImpl extends grpc.TestServerGrpc.TestServerImplBase {

        ArrayList<Integer> ones = new ArrayList<>();
        ArrayList<Integer> twos = new ArrayList<>();

        @Override
        public void requestOne(grpc.Request request, StreamObserver<grpc.Reply> responseObserver) {
            ones.add(request.getI());
            grpc.Reply reply = grpc.Reply.newBuilder().setMessage("Hello World, " + ones.size()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void requestTwo(grpc.Request request, StreamObserver<grpc.Reply> responseObserver) {
            twos.add(request.getI());
            grpc.Reply reply = grpc.Reply.newBuilder().setMessage("Bye World, " + twos.size()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
