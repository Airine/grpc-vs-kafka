package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Duration;
import java.util.ArrayList;

import static kafka.ConsumerCreator.createStringConsumer;
import static kafka.ProducerCreator.createStringProducer;

public class KafkaServer {
    ArrayList<String> ones;
    ArrayList<String> twos;

    public KafkaServer() {
        ones = new ArrayList<>();
        twos = new ArrayList<>();
    }

    public static void main(String[] args) {
        KafkaServer kafkaServer = new KafkaServer();
        KafkaConsumer<String, String> consumerOne = createStringConsumer("one");
        KafkaConsumer<String, String> consumerTwo = createStringConsumer("two");
        KafkaProducer<String, String> producerOne = createStringProducer();
        KafkaProducer<String, String> producerTwo = createStringProducer();

        int noMessageFound = 0;

        while (noMessageFound < 10000) {
            ConsumerRecords<String, String> consumerRecordsOne = consumerOne.poll(Duration.ofMillis(10));
            ConsumerRecords<String, String> consumerRecordsTwo = consumerTwo.poll(Duration.ofMillis(10));
            // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
            if (consumerRecordsOne.count() == 0 && consumerRecordsTwo.count() == 0) {
                noMessageFound++;
                continue;
            }

            noMessageFound = 0;

            //print each record.
            if (consumerRecordsOne.count() > 0) {
                consumerRecordsOne.forEach(record -> {
                    kafkaServer.ones.add(record.value());
                    String output = "Hello World, " + kafkaServer.ones.size();
//                    System.out.println(output);
                    producerOne.send(new ProducerRecord<>("outputOne", output));
                    producerOne.flush();
                });
                consumerOne.commitAsync();
            }

            if (consumerRecordsTwo.count() > 0) {
                consumerRecordsTwo.forEach(record -> {
                    kafkaServer.twos.add(record.value());
                    String output = "Bye World, " + kafkaServer.twos.size();
//                    System.out.println(output);
                    producerTwo.send(new ProducerRecord<>("outputTwo", output));
                    producerTwo.flush();
                });
                consumerTwo.commitAsync();
            }
        }
        consumerOne.close();
        consumerTwo.close();
        producerOne.close();
        producerTwo.close();
    }
}
