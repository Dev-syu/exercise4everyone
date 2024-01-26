package kr.co.ddoko.memberservice.common.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.ddoko.memberservice.domain.members.Member;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public void sendMemberMessage(Member member) {
        try {
            String memberJson = objectMapper.writeValueAsString(member);
            kafkaTemplate.send(topic, memberJson);
        } catch (JsonProcessingException e) {
            // JSON 직렬화 중에 오류가 발생하면 처리
            e.printStackTrace();
        }
    }

    public void memberUpdateTopic(Member member) {
        try {
            String memberJson = objectMapper.writeValueAsString(member);
            sendMessage("Match-listen", memberJson);
            //...
        } catch (JsonProcessingException e) {
            // JSON 직렬화 중에 오류가 발생하면 처리
            e.printStackTrace();
        }
    }

}