package kafka;

public interface IKafkaConstants {
    String KAFKA_BROKERS = "localhost:9092";

    String CLIENT_ID = "client1";

    Integer RETRY_CONFIG = 0;

    String ACK_CONFIG = "all";

    String GROUP_ID_CONFIG = "test";

    String OFFSET_RESET_EARLIER = "earliest";

    Integer MAX_POLL_RECORDS = 1;
}