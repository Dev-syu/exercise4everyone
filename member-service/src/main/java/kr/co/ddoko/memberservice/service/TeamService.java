package kr.co.ddoko.memberservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import kr.co.ddoko.memberservice.common.dto.TeamDto;
import kr.co.ddoko.memberservice.domain.team.Team;
import kr.co.ddoko.memberservice.domain.team.TeamRepository;
import kr.co.ddoko.memberservice.exception.team.DuplicateTeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TeamService {
    @PersistenceContext
    private EntityManager em;
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public Team saveTeam(TeamDto.SaveRequest saveRequest) {
        if (isDuplicateTeam(saveRequest)) {
            throw new DuplicateTeamException("이미 존재하는 팀입니다: " + saveRequest.getTitle());
        }
        return teamRepository.save(saveRequest.toEntity());
    }

    public void removeTeam(TeamDto.RemoveRequest removeRequest) {
        Optional<Team> existingTeam = findByTeam(removeRequest.getTitle(),removeRequest.getMaster(),removeRequest.getLocation());
        existingTeam.ifPresent(team -> em.remove(team));
    }

    public Optional<Team> findByTeam(String title, String master, String location) {
        try {
            String jpql = "SELECT m FROM Team m WHERE m.title = :title and m.master = :master and m.location = :location";

            return em.createQuery(jpql, Team.class)
                    .setParameter("title", title)
                    .setParameter("master",master)
                    .setParameter("location",location)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    private boolean isDuplicateTeam(TeamDto.SaveRequest team) {
        Optional<Team> existingTeam = findByTeam(team.getTitle(),team.getMaster(),team.getLocation());
        return existingTeam.isPresent();
    }
}
