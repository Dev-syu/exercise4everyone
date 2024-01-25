package kr.co.ddoko.memberservice.service;

import kr.co.ddoko.memberservice.common.dto.MemberDto;
import kr.co.ddoko.memberservice.common.dto.TeamDto;
import kr.co.ddoko.memberservice.common.dto.TeamInvolveDto;
import kr.co.ddoko.memberservice.domain.embedded.Permission;
import kr.co.ddoko.memberservice.domain.embedded.Sex;
import kr.co.ddoko.memberservice.domain.embedded.State;
import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.domain.team.Team;
import kr.co.ddoko.memberservice.domain.team.TeamInvolve;
import kr.co.ddoko.memberservice.domain.team.TeamInvolveRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TeamInvolveServiceTest {
    @Autowired
    private TeamInvolveService teamInvolveService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberService memberService;
    @Test
    @Rollback(value = false)
    void saveTeamInvolve() {
        MemberDto.SaveRequest memberSaveRequest = MemberDto.SaveRequest.builder()
                .id("dbtjd")
                .password("password123")
                .name("John Doe")
                .sex(Sex.female())
                .weight(70)
                .birthDay("1990-01-01")
                .belt(2)
                .phone("123-456-7890")
                .email("john.doe@example.com")
                .permission(Permission.user())
                .sleepAccount(false)
                .teamInvolve(null)
                .build();

        memberService.saveMember(memberSaveRequest);
        Member member = memberService.findById("dbtjd").get();

        TeamDto.SaveRequest teamSaveRequest = TeamDto.SaveRequest.builder()
                .title("NewJins")
                .master("ggl")
                .location("내맘")
                .build();

        teamService.saveTeam(teamSaveRequest);
        Team team = teamService.findByTeam("NewJins","ggl","내맘").get();

        TeamInvolveDto.SaveRequest saveRequest = TeamInvolveDto.SaveRequest.builder()
                .member(member)
                .team(team)
                .state(State.stay())
                .build();

        TeamInvolve teamInvolve = teamInvolveService.saveTeamInvolve(saveRequest);

        MemberDto.ChargeRequest chargeRequest  = MemberDto.ChargeRequest.builder()
                .id(member.getId())
                .newPassword(member.getPassword())
                .newName(member.getName())
                .sex(member.getSex())
                .newWeight(member.getWeight())
                .birthDay(member.getBirthDay())
                .newBelt(member.getBelt())
                .newPhone(member.getPhone())
                .newEmail(member.getEmail())
                .newPermission(member.getPermission())
                .newSleepAccount(member.isSleepAccount())
                .newTeamInvolve(teamInvolve)
                .build();

        memberService.updateMemberInfo(chargeRequest);
    }
}