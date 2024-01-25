package kr.co.ddoko.memberservice.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
@Embeddable
public class Permission {

    @Getter
    public enum Type {
        ADMIN(0),
        MASTER(1),
        MATCH_OPENER(2),
        MANAGER(3),
        USER(4);

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
    @Column(name = "permission_type")
    private Type value;

    private Permission(Type value) {
        this.value = value;
    }

    // 기본 생성자는 JPA 명세를 따르기 위해 필요
    protected Permission() {
    }

    public static Permission user() {
        return new Permission(Type.USER);
    }

    public static Permission master() {
        return new Permission(Type.MASTER);
    }

    public static Permission matchOpener() {
        return new Permission(Type.MATCH_OPENER);
    }

    public static Permission manager() {
        return new Permission(Type.MANAGER);
    }

    public static Permission admin() {
        return new Permission(Type.ADMIN);
    }

    public int getCode() {
        return value.getCode();
    }

    @Enumerated(EnumType.STRING)
    public void setValue(Type value) {
        this.value = value;
    }
}