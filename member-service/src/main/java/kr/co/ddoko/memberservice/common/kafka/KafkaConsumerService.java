package kr.co.ddoko.memberservice.common.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "Member-Service", groupId = "kafka-member")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        System.out.println("Received message: " + record.value());
    }
}
