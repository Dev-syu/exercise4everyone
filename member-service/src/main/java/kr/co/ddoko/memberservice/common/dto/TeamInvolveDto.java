package kr.co.ddoko.memberservice.common.dto;

import kr.co.ddoko.memberservice.domain.embedded.State;
import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.domain.team.Team;
import kr.co.ddoko.memberservice.domain.team.TeamInvolve;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamInvolveDto {

    @AllArgsConstructor
    @Getter
    public static abstract class BaseRequest {
        protected Long id;
        protected Member member;
        protected Team team;
        protected State state;

        public abstract TeamInvolve toEntity();
    }
    public static class SaveRequest extends BaseRequest{
        @Builder
        public SaveRequest(Long id, Member member, Team team, State state) {
            super(id, member,team,state);
        }

        @Override
        public TeamInvolve toEntity() {
            return TeamInvolve.builder()
                    .member(this.member)
                    .team(this.team)
                    .state(this.state)
                    .build();
        }
    }
    public static class ChangeRequest extends BaseRequest{

        private Team newTeam;
        private State newState;

        @Builder
        public ChangeRequest(Long id, Member member, Team newTeam, State newState) {
            super(id, member,newTeam,newState);
            this.member = member;
            this.newTeam = newTeam;
            this.newState = newState;
        }

        @Override
        public TeamInvolve toEntity() {
            return TeamInvolve.builder()
                    .member(this.member)
                    .team(this.newTeam)
                    .state(this.newState)
                    .build();
        }
    }

    public static class RemoveRequest extends BaseRequest{

        @Builder
        public RemoveRequest(Long id,Member member, Team team, State state) {
            super(id, member,team,state);
            this.state = state;
        }

        @Override
        public TeamInvolve toEntity() {
            return TeamInvolve.builder()
                    .member(this.member)
                    .team(this.team)
                    .state(this.state)
                    .build();
        }
    }



}
