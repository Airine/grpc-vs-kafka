package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import client.TestClient;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static kafka.ConsumerCreator.createStringConsumer;
import static kafka.ProducerCreator.createStringProducer;

public class KafkaClient implements TestClient {
    KafkaProducer<String, String> producer = createStringProducer();

    private String request(int i, String inputTopic, String outputTopic) {

        ProducerRecord<String, String> data = new ProducerRecord<>(inputTopic, Integer.toString(i));
        producer.send(data, new TestCallback());
        producer.flush();
        KafkaConsumer<String, String> consumer = createStringConsumer(outputTopic);
        AtomicInteger flag = new AtomicInteger(1000);
        while(flag.getAndDecrement()>0) {
            final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(5));

            if (records.count() == 0) {
                continue;
            }

            String value = records.iterator().next().value();
            consumer.commitAsync();
            consumer.close();
            return String.valueOf(value);
        }
        System.out.println("Time's up!");
        consumer.close();
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
