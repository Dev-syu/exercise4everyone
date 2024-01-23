package kr.co.ddoko.exercise4everyone.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

@Entity
public class Hello {
    @Id
    @GeneratedValue
    private Long id;

    private String hello;

    public Long getId() {
        return id;
    }

    public String getHello() {
        return hello;
    }

    public Hello() {

    }

    public Hello(String hello) {
        this.hello = hello;
    }
}

//public interface HelloRepository extends JpaRepository<Hello, Long> {
//}