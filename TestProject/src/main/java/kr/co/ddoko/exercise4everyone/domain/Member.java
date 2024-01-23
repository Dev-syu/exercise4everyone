package kr.co.ddoko.exercise4everyone.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
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

    /** 회원 정보 변경
     * */
    public void updatePassword(String password){
        this.password = password;
    }

    public void updateInfo(Member member){
        this.type = member.type;
        this.name = member.name;
        this.phone = member.phone;
        this.weight = member.weight;
        this.birth_date = member.birth_date;
        this.team = member.team;
    }
    public void MemberDTO(){}
}
