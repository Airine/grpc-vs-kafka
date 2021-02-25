# Communication Latency Benchmark over gRPC and Kafka

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

Ensure that the KafkaClient is not commented out, while the other two are.
```java
public static void main(String[] args){
    TestClient client =
        // new LocalClient();
        new KafkaClient();
        // new GrpcClient();
    // ...
}
```

_Remeber to adjust the `static int N` into a smaller value like 100 or 10._

1. Run [`src/main/java/kafka/PurgeAllTopics.java`](src/main/java/kafka/PurgeAllTopics.java) first to purge the data in these 
topics if there is any.
   
2. Run [`src/main/java/kafka/KafkaServer.java`](src/main/java/kafka/KafkaServer.java)

3. run [`src/main/java/Client.java`](src/main/java/Client.java)

### gRPC Client

To be implemented...
