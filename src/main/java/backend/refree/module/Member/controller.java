package backend.refree.module.Member;

import backend.refree.infra.config.jwtToken.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class controller {

    private final service service;

    // 회원가입
    @PostMapping("/signup")
    @ResponseBody
    public Object signup(@RequestBody @Valid MemberSignupDto memberSignupDto)  {
        MessageModel messageModel = service.signup(memberSignupDto);
        return messageModel;
    }

    // 로그인
    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody @Valid MemberLoginDto memberLoginDto, HttpServletResponse response) {
        MessageModel messageModel = service.login(memberLoginDto, response);
        return messageModel;
    }

}
