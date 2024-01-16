package kr.co.ddoko.exercise4everyone.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;
    private String name;
    private String master;

    public Team(){}
    public Team(String name, String master){
        this.name = name;
        this.master = master;
    }
}
