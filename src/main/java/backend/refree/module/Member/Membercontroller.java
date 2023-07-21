package backend.refree.module.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class Membercontroller {

    private final memberService memberService;

    // 회원가입
    @PostMapping("/signup")
    @ResponseBody
    public Object signup(@RequestBody @Valid MemberSignupDto memberSignupDto)  {
        MessageModel messageModel = memberService.signup(memberSignupDto);
        return messageModel;
    }

    // 로그인
    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody @Valid MemberLoginDto memberLoginDto, HttpServletResponse response) {
        MessageModel messageModel = memberService.login(memberLoginDto, response);
        return messageModel;
    }

}
