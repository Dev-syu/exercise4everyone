package kr.co.ddoko.memberservice.domain.members;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import kr.co.ddoko.memberservice.common.dto.MemberDto.ChargeRequest;
import kr.co.ddoko.memberservice.domain.embedded.Permission;
import kr.co.ddoko.memberservice.domain.embedded.Sex;
import kr.co.ddoko.memberservice.domain.team.TeamInvolve;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    private Long mid;
    private String id;
    private String password;
    private String name;
    private Sex sex;
    private int weight;
    private String birthDay;
    private int belt;
    private String phone;
    private String email;
    private Permission permission;
    private boolean sleepAccount;

    @OneToOne
    @JoinColumn(name = "teamInvolve_id")
    private TeamInvolve teamInvolve;

    public void updatePassword(ChargeRequest chargeRequest) {
        this.password = chargeRequest.getNewPassword();
    }
    public void updateInfo(ChargeRequest chargeRequest) {
        this.name = chargeRequest.getNewName();
        this.weight = chargeRequest.getNewWeight();
        this.belt = chargeRequest.getNewBelt();
        this.phone = chargeRequest.getNewPhone();
        this.email = chargeRequest.getNewEmail();
        this.permission = chargeRequest.getNewPermission();
        this.sleepAccount = chargeRequest.isNewSleepAccount();
        this.teamInvolve = chargeRequest.getTeamInvolve();;
    }
    @Builder
    public Member(Long mid, String id, String password, String name, Sex sex, int weight,
                  String birthDay, int belt, String phone, String email, Permission permission,
                  boolean sleepAccount, TeamInvolve teamInvolve) {
        this.mid = mid;
        this.id = id;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.weight = weight;
        this.birthDay = birthDay;
        this.belt = belt;
        this.phone = phone;
        this.email = email;
        this.permission = permission;
        this.sleepAccount = sleepAccount;
        this.teamInvolve = teamInvolve;
    }
}

