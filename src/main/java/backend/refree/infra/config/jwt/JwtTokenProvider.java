package backend.refree.infra.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Principal;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import io.jsonwebtoken.*;

import static backend.refree.module.Analysis.KomoranUtils.log;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${SECRET}")
    private String secretKey;

    // 토큰 유효시간 = 7일
    @Value("${EXPIRE_TIME}")
    private long tokenValidTime;

    private final PrincipalDetailsService userDetailsService;

    // 객체 초기화
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /*
    // Jwt 토큰 생성
    public String createToken(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String token = JWT.create()
                .withSubject("jwt-token")
                .withExpiresAt(new Date(System.currentTimeMillis() + 6000 * 60 * 24 * 7))
                .withClaim("email", principalDetails.getMember().getEmail())
                .sign(Algorithm.HMAC512(secretKey));

        return token;
    }

     */

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        String email = (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
        log.info("email: " + email);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Request의 Header에서 token 값을 가져온다. "Authorization": "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) return token.substring("Bearer ".length());
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
