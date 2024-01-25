package kr.co.ddoko.memberservice.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
public class Sex {

    @Getter
    public enum Type {
        FEMALE(0),
        MALE(1);

        private final int code;

        Type(int code) {
            this.code = code;
        }

        public static Type fromCode(int code) {
            for (Type value : values()) {
                if (value.code == code) {
                    return value;
                }
            }
            throw new IllegalArgumentException("Invalid code: " + code);
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "sex_type")
    private Type value;

    private Sex(Type value) {
        this.value = value;
    }

    // 기본 생성자는 JPA 명세를 따르기 위해 필요
    protected Sex() {
    }

    public static Sex female() {
        return new Sex(Type.FEMALE);
    }

    public static Sex male() {
        return new Sex(Type.MALE);
    }

    public int getCode() {
        return value.getCode();
    }

    @Enumerated(EnumType.STRING)
    public void setValue(Type value) {
        this.value = value;
    }
}