package kr.co.ddoko.memberservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import kr.co.ddoko.memberservice.common.dto.MemberDto;
import kr.co.ddoko.memberservice.common.dto.TeamInvolveDto;
import kr.co.ddoko.memberservice.domain.members.Member;
import kr.co.ddoko.memberservice.domain.team.TeamInvolve;
import kr.co.ddoko.memberservice.domain.team.TeamInvolveRepository;
import kr.co.ddoko.memberservice.exception.member.DuplicateMemberIdException;
import kr.co.ddoko.memberservice.exception.team.DuplicateTeamInvolveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TeamInvolveService {

    @PersistenceContext
    private EntityManager em;

    private final TeamInvolveRepository teamInvolveRepository;

    public TeamInvolveService(TeamInvolveRepository teamInvolveRepository){
        this.teamInvolveRepository = teamInvolveRepository;
    }

    public TeamInvolve saveTeamInvolve(TeamInvolveDto.SaveRequest saveRequest) {
        return teamInvolveRepository.save(saveRequest.toEntity());
    }

    public Optional<TeamInvolve> findByTid(Long tid) {
        try {
            String jpql = "SELECT m FROM team_involve m WHERE m.team_tid = :tid";

            return em.createQuery(jpql, TeamInvolve.class)
                    .setParameter("tid", tid)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public Optional<TeamInvolve> findByMid(Long mid) {
        try {
            String jpql = "SELECT m FROM team_involve m WHERE m.member_mid = :mid";

            return em.createQuery(jpql, TeamInvolve.class)
                    .setParameter("mid", mid)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public Optional<TeamInvolve> findByid(Long id) {
        TeamInvolve teamInvolve = em.find(TeamInvolve.class, id);
        return Optional.ofNullable(teamInvolve);
    }

    public void updateTeamInvolve(TeamInvolveDto.ChangeRequest changeRequest) {
        if (changeRequest.getId() != null) {
            TeamInvolve teamInvInfo = em.find(TeamInvolve.class, changeRequest.getId());
            teamInvInfo.updateTeamInvolve(changeRequest);
            em.persist(teamInvInfo);
        } else {
            // changeRequest.getMember()가 null이 아닐 때의 처리를 추가하려면 이 부분을 수정하세요.
            // 예를 들어, 이미 존재하는 엔티티를 업데이트하는 로직을 추가할 수 있습니다.
        }
    }
    public void removeTeamInvolve(TeamInvolveDto.RemoveRequest removeRequest) {
        TeamInvolve teamInvolve = em.find(TeamInvolve.class, removeRequest.getId());
        System.out.println("teamInvolve1: "+teamInvolve.getId());
        teamInvolve.removeTeamInvolve(removeRequest);
        System.out.println("teamInvolve2: "+teamInvolve.getId());
        em.remove(teamInvolve);
    }

}
