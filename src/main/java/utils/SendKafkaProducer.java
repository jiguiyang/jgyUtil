package utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

/**
 * @jiguiyang
 *
 * producer send kafka
 */
public class SendKafkaProducer {

    private static final String HOST = "172.16.236.91:6667";
    private static Producer<String, String> producer;

    static {
        Properties props = new Properties();
        props.put("bootstrap.servers", HOST);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
    }

    public static <T> void kafkaProducer(String topic, T obj) {
        String body = null;
        try {
            body = String.valueOf(JSONUtil.objectToJson(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Send->[" + body + "]");
        producer.send(new ProducerRecord<String, String>(topic, String.valueOf(new Date().getTime()), body));

    }

    public static void closeKafkaProducer() {
        producer.close();
    }
}