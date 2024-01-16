package kr.co.ddoko.exercise4everyone.api;

import kr.co.ddoko.exercise4everyone.domain.Hello;
import kr.co.ddoko.exercise4everyone.repository.HelloRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private final HelloRepository helloRepository;

    public HelloController(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @GetMapping("/hello")
    public ResponseEntity<List<Hello>> getAllHello() {
        return ResponseEntity.ok(helloRepository.findAll());
    }

    @PostMapping("/hello")
    public ResponseEntity<Long> setHello(@RequestBody String name) {
        Hello helloRes = helloRepository.save(new Hello(name));
        return ResponseEntity.ok(helloRes.getId());
    }
}