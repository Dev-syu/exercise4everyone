package kr.co.ddoko.exercise4everyone.repository;

import jakarta.persistence.EntityManager;
import kr.co.ddoko.exercise4everyone.domain.Member;
import kr.co.ddoko.exercise4everyone.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
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



}
