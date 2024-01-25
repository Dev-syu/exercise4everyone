package kr.co.ddoko.memberservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import kr.co.ddoko.memberservice.common.dto.MemberDto.*;
import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.domain.members.MemberRepository;
import kr.co.ddoko.memberservice.exception.member.DuplicateMemberIdException;
import kr.co.ddoko.memberservice.exception.member.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {
    @PersistenceContext
    private EntityManager em;
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Transactional
    public Member saveMember(SaveRequest saveRequest) {
        if (isDuplicateId(saveRequest.getId())) {
            throw new DuplicateMemberIdException("이미 존재하는 아이디입니다: " + saveRequest.getId());
        }
        return memberRepository.save(saveRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public boolean login(LoginRequest loginRequest) {
        existByEmailAndPassword(loginRequest);
        return true;
    }

    @Transactional
    public Optional<Member> findById(String id) {
        try {
            String jpql = "SELECT m FROM Member m WHERE m.id = :id";

            return em.createQuery(jpql, Member.class)
                    .setParameter("id", id)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    @Transactional(readOnly = true)
    public void existByEmailAndPassword(LoginRequest loginRequest) {
        // loginRequest.passwordEncryption(encryptionService);
        String id = loginRequest.getId();
        String password = loginRequest.getPassword();

        if (!memberRepository.existsByUserIdAndPassword(id, password)) {
            throw new UserNotFoundException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }
    @Transactional
    public Optional<Member> updateMemberPassword(ChargeRequest newPassword){

        try {
            Optional<Member> optionalMember = findById(newPassword.getId());

            optionalMember.ifPresent(member -> {
                member.updatePassword(newPassword);
                em.persist(member);
            });
            return optionalMember;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Member> updateMemberInfo(ChargeRequest newInfo){

        try {
            Optional<Member> optionalMember = findById(newInfo.getId());

            optionalMember.ifPresent(member -> {
                member.updateInfo(newInfo);
                em.persist(member);
            });
            return optionalMember;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void removeMember(RemoveRequest removeRequest) {
        Optional<Member> optionalMember = findById(removeRequest.getId());
        optionalMember.ifPresent(member -> em.remove(member));
    }
    private boolean isDuplicateId(String id) {
        Optional<Member> existingMember = findById(id);
        return existingMember.isPresent();
    }
}
