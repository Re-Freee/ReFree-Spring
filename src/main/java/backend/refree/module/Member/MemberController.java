package backend.refree.module.Member;

import backend.refree.infra.config.jwt.JwtTokenProvider;
import backend.refree.infra.exception.MemberException;
import backend.refree.infra.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<? extends BasicResponse> signup(@RequestBody @Valid MemberSignupDto memberSignupDto)  {
        return ResponseEntity.ok().body(memberService.signup(memberSignupDto));
    }

    @PostMapping("/login/search")
    public ResponseEntity<? extends BasicResponse> search(@RequestBody @Valid MemberPwSearchDto memberPwSearchDto) {
        return ResponseEntity.ok().body(memberService.search(memberPwSearchDto));
    }

    @PostMapping("/login/search/modify")
    public ResponseEntity<? extends BasicResponse> modify(@RequestBody @Valid MemberPwModifyDto memberPwModifyDto)  {
        return ResponseEntity.ok().body(memberService.modify(memberPwModifyDto));
    }

    // 토큰 인가 check 
    @GetMapping("/check")
    public ResponseEntity<? extends BasicResponse> check(Principal principal){
        if (principal == null) throw new MemberException("회원 정보를 찾을 수 없습니다.");
        return ResponseEntity.ok().body(memberService.check(principal.getName()));
    }

}
