package kr.co.ddoko.memberservice.domain.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
public class State {

    @Getter
    public enum Status {
        STAY(0),
        ALLOW(1),
        DENY(2);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public static Status fromCode(int code) {
            for (Status value : values()) {
                if (value.code == code) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Invalid code: " + code);
        }
    }

    private Status value;

    private State(Status value) {
        this.value = value;
    }

    // 기본 생성자는 JPA 명세를 따르기 위해 필요
    protected State() {
    }

    public static State stay() {
        return new State(Status.STAY);
    }

    public static State allow() {
        return new State(Status.ALLOW);
    }

    public static State deny() {
        return new State(Status.DENY);
    }

    public int getCode() {
        return value.getCode();
    }

    @Enumerated(EnumType.STRING)
    public void setValue(Status value) {
        this.value = value;
    }
}