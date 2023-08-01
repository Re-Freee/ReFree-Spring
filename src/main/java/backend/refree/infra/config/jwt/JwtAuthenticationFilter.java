package backend.refree.infra.config.jwt;

import backend.refree.infra.exception.MemberException;
import backend.refree.infra.response.SingleResponse;
import backend.refree.module.Member.MemberLoginDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final PrincipalDetailsService principalDetailsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.security.jwt.SECRET}")
    private final String secretKey;
    // login 요청 시 로그인 시도를 위해 실행
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("로그인 시도: JwtAuthenticationFilter.attemptAuthentication");

        try{
            MemberLoginDto login = objectMapper.readValue(request.getInputStream(), MemberLoginDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
            log.info(authenticationToken.getPrincipal().toString());
            log.info(authenticationToken.getCredentials().toString());

            String email = authenticationToken.getName();
            String password = (String) authenticationToken.getCredentials();

            UserDetails userDetails = principalDetailsService.loadUserByUsername(email);
            if (passwordEncoder.matches(password, userDetails.getPassword()) == false){
                throw new MemberException("비밀번호가 일치하지 않습니다.");
            }

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            log.info("LOGIN SUCCESS");

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("email: " + principalDetails.getMember().getEmail());
            log.info("password: " + principalDetails.getMember().getPassword());

            return authentication;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException{
        log.info("인증 완료: JwtAuthenticationFilter.successfulAuthentication");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject("jwt-token")
                .withExpiresAt(new Date(System.currentTimeMillis() + 6000 * 60 * 24 * 7))
                .withClaim("email", principalDetails.getMember().getEmail())
                .sign(Algorithm.HMAC512(secretKey));

        response.setHeader("Authorization", "Bearer " + token);

        response.setContentType("application/json");
        SingleResponse singleResponse = new SingleResponse(200, "Login Complete!");
        String result = objectMapper.writeValueAsString(singleResponse);
        response.getWriter().write(result);

        log.info("token: Bearer " + token);
    }
}
