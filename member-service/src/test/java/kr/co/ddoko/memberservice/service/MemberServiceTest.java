package kr.co.ddoko.memberservice.service;

import kr.co.ddoko.memberservice.common.dto.MemberDto;
import kr.co.ddoko.memberservice.common.dto.TeamDto;
import kr.co.ddoko.memberservice.domain.embedded.Permission;
import kr.co.ddoko.memberservice.domain.embedded.Sex;
import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.exception.member.DuplicateMemberIdException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
//    @Rollback(value = false)
    public void saveRequestToEntityTest() {
        // Given
        MemberDto.SaveRequest saveRequest = MemberDto.SaveRequest.builder()
                .id("john_doe")
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

        // When
        Member savedMember = memberService.saveMember(saveRequest);

        // Then
        Optional<Member> findMember = memberService.findById("john_doe");
        assertTrue(findMember.isPresent(), "멤버가 저장되어야 합니다.");
    }


    @Test
//    @Rollback(value = false)
    public void UpdateMemberPasswordTest() {
        // Given
        Member originalMember = memberService.findById("john_doe").orElseThrow();

        MemberDto.ChargeRequest chargeRequest = MemberDto.ChargeRequest.builder()
                .id(originalMember.getId())
                .newPassword("1234")
                .newName(originalMember.getName())
                .sex(originalMember.getSex())
                .newWeight(originalMember.getWeight())
                .birthDay(originalMember.getBirthDay())
                .newBelt(originalMember.getBelt())
                .newPhone(originalMember.getPhone())
                .newEmail(originalMember.getEmail())
                .newPermission(originalMember.getPermission())
                .newSleepAccount(originalMember.isSleepAccount())
                .newTeamInvolve(originalMember.getTeamInvolve())
                .build();

        // When
        memberService.updateMemberPassword(chargeRequest);

        // Then
        Member updatedMember = memberService.findById("john_doe").orElseThrow();
        assertEquals("1234", updatedMember.getPassword(), "비밀번호가 업데이트되어야 합니다.");
        assertEquals(originalMember.getName(), updatedMember.getName(), "이름은 변경되지 않아야 합니다.");

    }
    @Test
    void updateMemberInfoTest() {
        // Given
        Member originalMember = memberService.findById("john_doe").orElseThrow();

        MemberDto.ChargeRequest chargeRequest = MemberDto.ChargeRequest.builder()
                .id(originalMember.getId())
                .newPassword("1234")
                .newName("김창식")
                .sex(originalMember.getSex())
                .newWeight(originalMember.getWeight())
                .birthDay(originalMember.getBirthDay())
                .newBelt(originalMember.getBelt())
                .newPhone(originalMember.getPhone())
                .newEmail(originalMember.getEmail())
                .newPermission(originalMember.getPermission())
                .newSleepAccount(originalMember.isSleepAccount())
                .newTeamInvolve(originalMember.getTeamInvolve())
                .build();

        // When
        Optional<Member> updatedMemberOptional = memberService.updateMemberInfo(chargeRequest);

        // Then
        assertTrue(updatedMemberOptional.isPresent(), "업데이트된 회원이 존재해야 합니다.");

        // 검증: 업데이트된 비밀번호와 일치하는지 확인
        Member updatedMember = updatedMemberOptional.get();
        assertEquals(chargeRequest.getName(), updatedMember.getName(), "이름이 올바르게 업데이트되어야 합니다.");
    }

    @Test
//    @Rollback(value = false)
    void removeMemberTest() {
        // Given
        Member originalMember = memberService.findById("john_doe").orElseThrow();

        MemberDto.RemoveRequest removeRequest = MemberDto.RemoveRequest.builder()
                .id(originalMember.getId())
                .build();

        // When
        memberService.removeMember(removeRequest);

        // Then
        Optional<Member> removedMemberOptional = memberService.findById("john_doe");

        assertFalse(removedMemberOptional.isPresent(), "회원이 삭제되어야 합니다.");
    }

}
