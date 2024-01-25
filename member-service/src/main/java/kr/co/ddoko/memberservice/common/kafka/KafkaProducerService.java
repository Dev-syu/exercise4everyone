package kr.co.ddoko.memberservice.common.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Setter
    @Getter
    private String topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // 생성자를 통해 topic 초기화
    public KafkaProducerService() {
        this.topic = "test-send";
    }

    public KafkaProducerService(String topic) {
        this.topic = topic;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(topic, message);
    }
    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }


}