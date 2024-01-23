package kr.co.ddoko.exercise4everyone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class MatchHistory {

    @Id
    @GeneratedValue
    @Column(name = "match_history_id")
    private Long id;
    @JoinColumn(name = "match_attend_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MatchAttend matchAttend;
    private String awards;
}
