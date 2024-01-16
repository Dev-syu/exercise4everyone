package kr.co.ddoko.exercise4everyone.repository;

import jakarta.persistence.EntityManager;
import kr.co.ddoko.exercise4everyone.domain.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamRepository {

    private final EntityManager em;
    public Long save(Team team) {
        em.persist(team);
        return team.getId();
    }

    public Team findByMid(Long mid){
        return em.find(Team.class, mid);
    }

    public Optional<Team> findByName(String name){
        return em.createQuery("select m from Team m where m.name = :name", Team.class)
                .setParameter("name",name)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<Team> findAll(){
        return em.createQuery("select m from Team m", Team.class)
                .getResultList();
    }

}
