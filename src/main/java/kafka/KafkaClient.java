package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import server.TestClient;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaClient implements TestClient {

    Producer<Long, Integer> producer = new ProducerCreator<Integer>().createProducer();

    private String request(int i, String inputTopic, String outputTopic) {
        ProducerRecord<Long, Integer> data = new ProducerRecord<>(inputTopic, i);
        producer.send(data, new TestCallback());
        producer.flush();
        Consumer<Long, String> consumer = new ConsumerCreator<String>().createConsumer(outputTopic);
//        ConsumerRecords<Long, String> records = consumer.poll(Duration.ofSeconds(1));
        AtomicInteger flag = new AtomicInteger(100);
        while(flag.getAndDecrement()>0) {
            final ConsumerRecords<Long, String> records = consumer.poll(Duration.ofSeconds(1));
            if (records.count() == 0) {
                continue;
            }
            String value = records.iterator().next().value();
            consumer.commitAsync();
            consumer.close();
            return value;
        }
        return "None";
    }

    public String requestOne(int i) {
        return request(i, "one", "outputOne");
    }

    public String requestTwo(int i) {
        return request(i, "two", "outputTwo");
    }

    private static class TestCallback implements Callback {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            // Purpose is to report problems when there are
            if (e != null) {
                System.out.println("Error while producing message to topic :" + recordMetadata);
                e.printStackTrace();
            }
        }
    }
}
