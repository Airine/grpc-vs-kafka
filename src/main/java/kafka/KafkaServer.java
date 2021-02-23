package kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;

public class KafkaServer {
    ArrayList<Integer> ones;
    ArrayList<Integer> twos;

    public KafkaServer() {
        ones = new ArrayList<Integer>();
        twos = new ArrayList<Integer>();
    }

    public static void main(String[] args) {
        KafkaServer kafkaServer = new KafkaServer();
        Consumer<Long, Integer> consumerOne = new ConsumerCreator<Integer>().createConsumer("one");
        Consumer<Long, Integer> consumerTwo = new ConsumerCreator<Integer>().createConsumer("two");
        Producer<Long, String> producerOne = new ProducerCreator<String>().createProducer();
        Producer<Long, String> producerTwo = new ProducerCreator<String>().createProducer();

        int noMessageFound = 0;

        while (true) {
            ConsumerRecords<Long, Integer> consumerRecordsOne = consumerOne.poll(Duration.ofSeconds(1));
            ConsumerRecords<Long, Integer> consumerRecordsTwo = consumerOne.poll(Duration.ofSeconds(1));
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecordsOne.count() == 0 && consumerRecordsTwo.count() == 0) {
                noMessageFound++;
//                if (noMessageFound > IKafkaConstants.MAX_NO_MESSAGE_FOUND_COUNT)
//                    // If no message found count is reached to threshold exit loop.
//                    break;
//                else
                System.out.println("no msg = " + noMessageFound);
                continue;
            }

            //print each record.
            if (consumerRecordsOne.count() > 0) {
                consumerRecordsOne.records("one").forEach(record -> {
                    kafkaServer.ones.add(record.value());
                    String output = "Hello World, " + kafkaServer.ones.size();
                    System.out.println(output);
                    producerOne.send(new ProducerRecord<>("outputOne", output));
                });
                consumerOne.commitAsync();
            }

            if (consumerRecordsTwo.count() > 0) {
                consumerRecordsTwo.records("two").forEach(record -> {
                    kafkaServer.twos.add(record.value());
                    String output = "Bye World, " + kafkaServer.twos.size();
                    System.out.println(output);
                    producerTwo.send(new ProducerRecord<>("outputTwo", output));
                });
                consumerTwo.commitAsync();
            }
        }
    }
}
