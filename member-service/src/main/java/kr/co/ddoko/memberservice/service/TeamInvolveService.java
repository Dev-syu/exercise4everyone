package kr.co.ddoko.memberservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
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

    public void removeTeamInvolve(TeamInvolveDto.RemoveRequest removeRequest) {
        Optional<TeamInvolve> optionalTeamInvolve = findByMid(removeRequest.getMember().getMid());
        optionalTeamInvolve.ifPresent(involve -> em.remove(involve));
    }

}
