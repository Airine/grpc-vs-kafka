package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

import static kafka.ConsumerCreator.createStringConsumer;

public class PurgeAllTopics {
    public static void main(String[] args) {
        // Establish Kafka consumers to consume all the topics one by one, so that there is not reminiscent stuff
        String[] topics = new String[]{// put all possible topics here!
                "one",
                "two",
                "outputOne",
                "outputTwo"
        };
        for(String topic:topics){
            int counts = 0;
            System.out.println("Purging the topic: " + topic);
            KafkaConsumer<String, String> tmpConsumer = createStringConsumer(topic);
            ConsumerRecords<String, String> consumerRecords =
                    tmpConsumer.poll(Duration.ofMillis(5));
            while(consumerRecords.count()>0){
                counts += consumerRecords.count();
                consumerRecords = tmpConsumer.poll(Duration.ofMillis(5));
            } // Purge the messages one by one!!
            System.out.println(String.format("Topic %s has been purged with %d remained messages.", topic, counts));
            tmpConsumer.close();
        }
    }
}
