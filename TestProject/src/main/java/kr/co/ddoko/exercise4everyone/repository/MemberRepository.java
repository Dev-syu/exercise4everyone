package kr.co.ddoko.exercise4everyone.repository;

import jakarta.persistence.EntityManager;
import kr.co.ddoko.exercise4everyone.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getMid();
    }

    public Member findByMid(Long mid){
        return em.find(Member.class, mid);
    }

    public Optional<Member> findByID(String id){
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id",id)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Optional<Member> findByInfo(String name, String phone){
        return em.createQuery("select m from Member m where m.name = :name AND m.phone = :phone", Member.class)
                .setParameter("name",name)
                .setParameter("phone",phone)
                .getResultStream().findFirst();
    }

    public void updateInfo(Member originMember, Member modifyMember){
        originMember.updateInfo(modifyMember);
    }

    public void updatePassword(Member originMember, String password){
        originMember.updatePassword(password);
    }

    public void deleteMember(Member member){
        em.remove(member);
    }
}
