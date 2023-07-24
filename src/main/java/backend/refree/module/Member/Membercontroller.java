package backend.refree.module.Member;

import backend.refree.infra.config.jwt.JwtTokenProvider;
import backend.refree.infra.exception.MemberException;
import backend.refree.infra.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class Membercontroller {

    private final memberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<? extends BasicResponse> signup(@RequestBody @Valid MemberSignupDto memberSignupDto)  {
        return ResponseEntity.ok().body(memberService.signup(memberSignupDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<? extends BasicResponse> login(@RequestBody @Valid MemberLoginDto memberLoginDto, HttpServletResponse response) {
        return ResponseEntity.ok().body(memberService.login(memberLoginDto, response));
    }

    // 토큰 인가
    @GetMapping("/check")
    public ResponseEntity<? extends BasicResponse> check(Principal principal){
        if (principal == null) throw new MemberException("회원 정보를 찾을 수 없습니다.");
        return ResponseEntity.ok().body(memberService.check(principal.getName()));
    }

}
