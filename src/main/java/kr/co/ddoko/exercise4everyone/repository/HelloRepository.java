package kr.co.ddoko.exercise4everyone.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.co.ddoko.exercise4everyone.domain.Hello;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HelloRepository {
    private final EntityManager em;

    @Transactional
    public Hello save(Hello hello){
        em.persist(hello);
        System.out.println("Save complete");
        return hello;
    }

    @Transactional
    public List<Hello> findAll() {
        return em.createQuery("select m from Hello m", Hello.class)
                .getResultList();
    }
}
