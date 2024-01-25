package kr.co.ddoko.memberservice.controller;

import kr.co.ddoko.memberservice.common.dto.MemberDto;
import kr.co.ddoko.memberservice.service.MemberService;
import kr.co.ddoko.memberservice.service.TeamInvolveService;
import kr.co.ddoko.memberservice.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberApiController {
    private final MemberService memberService;
    private final TeamService teamService;
    private final TeamInvolveService teamInvolveService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.LoginRequest loginRequest) {
        boolean result = memberService.login(loginRequest);
        if (result) {
            return ResponseEntity.ok().body("로그인 성공했습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
        }
    }
}
