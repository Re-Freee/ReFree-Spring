package backend.refree.module.Member;

import backend.refree.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class controller {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final repository memberRepository;

    // 회원가입
    @PostMapping("/signup")
    public int signup(@RequestBody Map<String, String> user) {
        return memberRepository.save(memberEntity.builder()
                .email(user.get("email"))
                .password(passwordEncoder.encode(user.get("password")))
                .checkPassword(passwordEncoder.encode(user.get("checkPassword")))
                .nickname(user.get("nickname"))
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user){
        memberEntity member = memberRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        if (!passwordEncoder.matches(user.get("password"), member.getPassword())){
            throw new IllegalArgumentException("회원 정보가 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(member.getEmail());
    }
}
