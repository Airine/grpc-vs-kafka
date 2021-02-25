# Communication Latency Benchmark over gRPC and Kafka

## Benchmark Result

### Latency Benchmark

| Technique | Avg. Latency (ms) | Total Latency (ms) | # of Invoke |
| --------- | ----------------- | ------------------ | ----------- |
| Local     | 0.0041            | 41                 | 10000       |
| RMI       | 0.049             | 490                | 10000       |
| gRPC      | 0.2526            | 2526               | 10000       |
| Kafka     | 3017.3            | 30173              | 10          |

### Implementation Effort

*LoC: Lines of Code*

| Technique | Total LoC | Client LoC | Server LoC | Utils |
| --------- | --------- | ---------- | ---------- | ----- |
| Local     | 23        | 23         | 0          | 0     |
| RMI       | 97        | 42         | 46         | 9     |
| gRPC      | 116       | 49         | 48         | 19    |
| Kafka     | 243       | 59         | 71         | 113   |

## How to Run

### Local Client

Ensure that the LocalClient is not commented out, while the other two are.
```java
public static void main(String[] args){
    TestClient client =
        new LocalClient();
        // new KafkaClient();
        // new GrpcClient();
    // ...
}
```

Just run [`src/main/java/Client.java`](src/main/java/Client.java), then you may get output like:

```text
...
Total runtime = 40 ms
Average latency = 0.004 ms
```

### Kafka Client

Ensure that the LocalClient is not commented out, while the other two are.
```java
public static void main(String[] args){
    TestClient client =
        // new LocalClient();
        new KafkaClient();
        // new GrpcClient();
    // ...
}
```

1. Run [`src/main/java/kafka/PurgeAllTopics.java`](src/main/kafka/PurgeAllTopics.java) first to purge the data in these 
topics if there is any.
   
2. Run [`src/main/java/kafka/KafkaServer.java`](src/main/kafka/KafkaServer.java)

3. Run [`src/main/java/Client.java`](src/main/java/Client.java)

### gRPC Client

Ensure that the LocalClient is not commented out, while the other two are.
```java
public static void main(String[] args){
    TestClient client =
        // new LocalClient();
        // new KafkaClient();
        new GrpcClient();
    // ...
}
```

1. Use maven to compile the [`client.proto`](src/main/proto/server.proto)
```bash
mvn clean compile
```

2. Run [`src/main/java/grpc/GrpcServer.java`](src/main/java/grpc/GrpcServer.java)

3. Run [`src/main/java/Client.java`](src/main/java/Client.java)