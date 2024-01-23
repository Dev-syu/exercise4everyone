package kr.co.ddoko.exercise4everyone.repository;

import jakarta.persistence.EntityManager;
import kr.co.ddoko.exercise4everyone.domain.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MatchRepository {
    private final EntityManager em;

    public Long save(Match match){
        em.persist(match);
        return match.getId();
    }

    public Optional<Match> findByTitle(String title){
        return em.createQuery("SELECT * FROM Match WHERE title = :title",Match.class)
                .setParameter("title",title)
                .getResultList()
                .stream()
                .findFirst();
    }

    public List<Match> findAll(){
        return em.createQuery("SELECT * FROM Match",Match.class)
                .getResultList();
    }
}
