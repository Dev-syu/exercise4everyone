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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    //@Rollback(value = false)
    void saveTeamInvolve() {
        MemberDto.SaveRequest memberSaveRequest = MemberDto.SaveRequest.builder()
                .id("dbtjd123")
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
        Member member = memberService.findById("dbtjd123").get();

        TeamDto.SaveRequest teamSaveRequest = TeamDto.SaveRequest.builder()
                .title("NewJins12")
                .master("ggl12")
                .location("내맘12")
                .build();

        teamService.saveTeam(teamSaveRequest);
        Team team = teamService.findByTeam("NewJins12", "ggl12", "내맘12").get();

        TeamInvolveDto.SaveRequest saveRequest = TeamInvolveDto.SaveRequest.builder()
                .member(member)
                .team(team)
                .state(State.stay())
                .build();

        // Act
        TeamInvolve teamInvolve = teamInvolveService.saveTeamInvolve(saveRequest);

        // Assert
        assertNotNull(teamInvolve, "팀 인보크가 저장되어야 합니다.");
        assertNotNull(teamInvolve.getId(), "팀 인보크 ID가 설정되어야 합니다.");
        assertEquals(member, teamInvolve.getMember(), "팀 인보크의 멤버가 올바르게 설정되어야 합니다.");
        assertEquals(team, teamInvolve.getTeam(), "팀 인보크의 팀이 올바르게 설정되어야 합니다.");
        System.out.println("teamInvolve.getState()"+teamInvolve.getState().getValue());
        System.out.println("teamInvolve.getState()"+State.stay());
        assertEquals(State.stay().getValue(), teamInvolve.getState().getValue(), "팀 인보크의 상태가 올바르게 설정되어야 합니다.");
    }

}