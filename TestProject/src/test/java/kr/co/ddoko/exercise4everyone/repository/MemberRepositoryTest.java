package kr.co.ddoko.exercise4everyone.repository;

import kr.co.ddoko.exercise4everyone.domain.Member;
import kr.co.ddoko.exercise4everyone.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
//    @Autowired MemberService memberService;
    @Autowired
    TeamRepository teamRepository;

    @Test
    @Transactional
//    @Rollback(value = false)
    void save() {
        Team team = new Team("킹덤", "바보");
        teamRepository.save(team);

        Member member1 = new Member();
//        member1.setId("syu");
//        member1.setName("유성");
//        member1.setPhone("010-2635-6673");
//        member1.setSex(Sex.male);
//        member1.setPassword("1234");
//        member1.setType(1);
//        member1.setTeam(team);
//        member1.setWeight(75);
//        member1.setSleeper_account(SleepStatus.alive);
//        member1.setBirth_date("19960110");

        Long test = memberRepository.save(member1);
        Member member2 = memberRepository.findByMid(test);

        Assertions.assertThat(member1.getId()).isEqualTo(member2.getId());
        System.out.println("test: "+member2.getBirth_date());

    }

    @Test
    @Transactional
    void findByMid() {
        Team team = new Team("하이브", "김승훈");
        teamRepository.save(team);

        Member member1 = new Member();
//        member1.setId("syu");
//        member1.setName("유성");
//        member1.setPhone("010-2635-6673");
//        member1.setSex(Sex.male);
//        member1.setPassword("1234");
//        member1.setType(1);
//        member1.setTeam(team);
//        member1.setWeight(75);
//        member1.setSleeper_account(SleepStatus.alive);
//        member1.setBirth_date("19960110");

        Long test = memberRepository.save(member1);
        Member member2 = memberRepository.findByMid(test);

        Assertions.assertThat(member1.getId()).isEqualTo(member2.getId());
        System.out.println("test: "+member2.getBirth_date());
    }

    @Test
    @Transactional
    void findByID() {

        // When
        Optional<Member> foundMember = memberRepository.findByID("syu");

        // Then
        if(foundMember.isPresent()){
            System.out.println("test: "+foundMember.get().getPhone());
            Assertions.assertThat(foundMember.get().getId()).isEqualTo("syu");
        }

    }

    @Test
    @Transactional
    void findByInfo() {
        // When
        Optional<Member> foundMember = memberRepository.findByInfo("유성","010-2635-6673");

        // Then
        if(foundMember.isPresent()){
            System.out.println("test: "+foundMember.get().getPhone());
            Assertions.assertThat(foundMember.get().getId()).isEqualTo("syu");
        }

    }

    @Test
    @Transactional
    //@Rollback(value = false)
    void 비밀번호변경() {
        // When
        Optional<Member> foundMember = memberRepository.findByInfo("유성","010-2635-6673");

        // Then
        if(foundMember.isPresent()){
            Member member = foundMember.get();

            System.out.println("업데이트 전");
            System.out.println(member.getPassword());
            System.out.println("업데이트 후");
            member.updatePassword("1234");
            System.out.println(member.getPassword());
            Assertions.assertThat(member.getPassword()).isEqualTo("1234");

        }

    }
}