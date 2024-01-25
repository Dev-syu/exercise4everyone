package kr.co.ddoko.memberservice.common.dto;

import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.domain.team.Team;
import kr.co.ddoko.memberservice.domain.team.TeamInvolve;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamDto {
    @AllArgsConstructor
    @Getter
    public static abstract class BaseRequest {
        protected Long tid;
        protected String title;
        protected String master;
        protected String location;
        public abstract Team toEntity();
    }


    public static class SaveRequest extends BaseRequest {
        @Builder
        public SaveRequest(Long tid, String title,String master, String location) {
            super(tid, title, master, location);
        }

        @Override
        public Team toEntity() {
            return Team.builder()
                    .title(this.title)
                    .master(this.master)
                    .location(this.location)
                    .build();
        }
    }
    public static class RemoveRequest extends BaseRequest {
        @Builder
        public RemoveRequest(Long tid, String title, String master, String location) {
            super(tid, title, master, location);
        }

        @Override
        public Team toEntity() {
            return Team.builder()
                    .title(this.title)
                    .master(this.master)
                    .location(this.location)
                    .build();
        }
    }
}
