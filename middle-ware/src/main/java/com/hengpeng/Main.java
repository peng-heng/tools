package com.hengpeng;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "172.20.92.224:9092");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000; i++) {
            kafkaProducer.send(new ProducerRecord<>("test", String.valueOf(System.currentTimeMillis())),
                    new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            System.out.println("send message to partition " + metadata.partition() + ", offset " + metadata.offset());
                            if (exception != null) {
                                exception.printStackTrace();
                            }
                        }
                    });
        }
        Thread.sleep(2000000);
    }
}
