package kr.co.ddoko.exercise4everyone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long mid;//기본키
    private String id;
    private String password;
    private int type;
    @Enumerated(EnumType.STRING)
    private SleepStatus sleeper_account; //ENUM [alive(생존), sleep(휴면)]
    private String name;
    private String phone;
    private int weight;
    @Enumerated(EnumType.STRING)
    private Sex sex; //ENUM [male(남자), female(여자)]
    private String birth_date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
