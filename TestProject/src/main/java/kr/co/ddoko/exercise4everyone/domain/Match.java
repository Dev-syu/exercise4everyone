package kr.co.ddoko.exercise4everyone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter
public class Match {
    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private Long id;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Date regist_date;
    private Date start_date;
    private Date end_date;
    @Enumerated(EnumType.ORDINAL)
    private Weight weight;

}
