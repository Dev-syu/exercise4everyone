package kr.co.ddoko.memberservice.domain.team;

import jakarta.persistence.*;
import kr.co.ddoko.memberservice.common.dto.TeamInvolveDto;
import kr.co.ddoko.memberservice.domain.embedded.State;
import kr.co.ddoko.memberservice.domain.members.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamInvolve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_mid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "team_tid")
    private Team team;

    @Embedded
    private State state;

    public void updateTeamInvolve(TeamInvolveDto.ChangeRequest changeRequest){
        this.member = changeRequest.getMember();
        this.team = changeRequest.getTeam();
        this.state = changeRequest.getState();
    }

    public void removeTeamInvolve(TeamInvolveDto.RemoveRequest removeRequest){
        this.id = removeRequest.getId();
    }
    public void banishTeamInvolve(TeamInvolveDto.RemoveRequest removeRequest){
        this.state = removeRequest.getState();
    }

    @Builder
    public TeamInvolve(Member member, Team team, State state) {
        this.member = member;
        this.team = team;
        this.state = state;
    }
}