package kr.co.ddoko.memberservice.domain.team;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    @Id
    @GeneratedValue
    private Long tid;
    private String title;
    private String master;
    private String location;

    @Builder
    public Team(Long tid, String title, String master, String location){
        this.tid = tid;
        this.title = title;
        this.master = master;
        this.location = location;
    }
}
