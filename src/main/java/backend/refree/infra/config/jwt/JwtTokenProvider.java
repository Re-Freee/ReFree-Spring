package backend.refree.infra.config.jwt;

import backend.refree.infra.exception.MemberException;
import backend.refree.module.Member.MemberRepository;
import backend.refree.module.Member.Member;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtTokenProvider( @Value("${spring.security.jwt.SECRET}") String secretKey
            , MemberRepository memberRepository, PasswordEncoder passwordEncoder){
        this.secretKey = secretKey;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existByEmail(String email) {
        return memberRepository.existsMemberByEmail(email);
    }

    public boolean verifyPassword(String password, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("존재하지 않는 회원"));
        return passwordEncoder.matches(password, member.getPassword());
    }

    @Transactional
    public String createAccessToken(String email) {
        return JWT.create()
                .withSubject("jwt-token")
                .withExpiresAt(new Date(System.currentTimeMillis() + 60480000))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secretKey));
    }

    @Transactional
    public void createTokenAndAddHeader(Member member, HttpServletResponse response) {
        String accessToken = createAccessToken(member.getEmail());
        response.addHeader("Authorization", "Bearer " + accessToken);
    }

    // Request의 Header에서 token 값을 가져온다. "Authorization": "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer"))
            throw new IllegalArgumentException("INVALID_TOKEN");
        return token.substring("Bearer ".length());
    }

    // 토큰의 유효성 + 만료일자 확인
    public Member validateTokenAndFindMember(String token) {
        String email = JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token)
                .getClaim("email").asString();
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberException("존재하지 않는 회원"));
    }

}
