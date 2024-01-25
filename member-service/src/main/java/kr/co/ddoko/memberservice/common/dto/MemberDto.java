package kr.co.ddoko.memberservice.common.dto;

import jakarta.validation.constraints.Pattern;
import kr.co.ddoko.memberservice.domain.embedded.Permission;
import kr.co.ddoko.memberservice.domain.embedded.Sex;
import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.domain.team.TeamInvolve;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDto {
    @AllArgsConstructor
    @Getter
    public static abstract class BaseRequest {

        protected Long mid;
        protected String id;
        protected String password;
        protected String name;
        protected Sex sex;
        protected int weight;
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "유효한 생년월일 형식이어야 합니다.")
        protected String birthDay;
        protected int belt;
        @Pattern(regexp = "^01(?:0|1|[6-9])-(\\d{3}|\\d{4})-\\d{4}$", message = "유효한 핸드폰 번호 형식이어야 합니다.")
        protected String phone;
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "유효한 이메일 주소 형식이어야 합니다.")
        protected String email;
        protected Permission permission;
        protected boolean sleepAccount;
        protected TeamInvolve teamInvolve;

        public abstract Member toEntity();
    }

    public static class SaveRequest extends BaseRequest {

        @Builder
        public SaveRequest(Long mid, String id, String password, String name,
                           Sex sex, int weight, String birthDay, int belt,
                           String phone, String email, Permission permission, boolean sleepAccount, TeamInvolve teamInvolve) {
            super(mid, id, password, name, sex, weight, birthDay, belt, phone, email, permission, sleepAccount, teamInvolve);
        }

        @Override
        public Member toEntity() {
            return Member.builder()
                    .id(this.id)
                    .password(this.password)
                    .name(this.name)
                    .sex(this.sex)
                    .weight(this.weight)
                    .birthDay(this.birthDay)
                    .belt(this.belt)
                    .phone(this.phone)
                    .email(this.email)
                    .permission(this.permission)
                    .sleepAccount(this.sleepAccount)
                    .teamInvolve(this.teamInvolve)
                    .build();
        }
    }

    @Getter
    public static class ChargeRequest extends BaseRequest {

        private String newPassword;
        private String newName;
        private int newWeight;
        private int newBelt;
        private String newPhone;
        private String newEmail;
        private Permission newPermission;
        private boolean newSleepAccount;
        private TeamInvolve newTeamInvolve;

        @Builder
        public ChargeRequest(Long mid, String id, String newPassword, String newName,
                             Sex sex, int newWeight, String birthDay, int newBelt,
                             String newPhone, String newEmail, Permission newPermission, boolean newSleepAccount, TeamInvolve newTeamInvolve) {
            super(mid, id, newPassword, newName, sex, newWeight, birthDay, newBelt, newPhone, newEmail, newPermission, newSleepAccount, newTeamInvolve);
            this.newPassword = newPassword;
            this.newName = newName;
            this.newWeight = newWeight;
            this.newBelt = newBelt;
            this.newPhone = newPhone;
            this.newEmail = newEmail;
            this.newPermission = newPermission;
            this.newSleepAccount = newSleepAccount;
            this.newTeamInvolve = newTeamInvolve;
        }

        @Override
        public Member toEntity() {
            return Member.builder()
                    .id(this.id)
                    .password(this.newPassword)
                    .name(this.newName)
                    .sex(this.sex)
                    .weight(this.newWeight)
                    .birthDay(this.birthDay)
                    .belt(this.newBelt)
                    .phone(this.newPhone)
                    .email(this.newEmail)
                    .permission(this.newPermission)
                    .sleepAccount(this.newSleepAccount)
                    .teamInvolve(this.newTeamInvolve)
                    .build();
        }
    }
    public static class RemoveRequest extends BaseRequest {

        @Builder
        public RemoveRequest(String id) {
            super(null, id, null, null, Sex.female(), -1, null, -1, null, null,Permission.user(), false,null);
        }

        @Override
        public Member toEntity() {
            return Member.builder()
                    .id(this.id)
                    .build();
        }
    }

    public static class LoginRequest extends BaseRequest {

        @Builder
        public LoginRequest(String id, String password) {
            super(null, id, password, null, Sex.female(), -1, null, -1, null, null,Permission.user(), false,null);
        }

        @Override
        public Member toEntity() {
            return Member.builder()
                    .id(this.id)
                    .password(this.password)
                    .build();
        }
    }

}
