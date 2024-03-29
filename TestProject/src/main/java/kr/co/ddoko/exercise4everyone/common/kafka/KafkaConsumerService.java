package kr.co.ddoko.exercise4everyone.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "test-listen", groupId = "test-1")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
    }
}
