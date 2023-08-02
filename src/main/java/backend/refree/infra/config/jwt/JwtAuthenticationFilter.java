package backend.refree.infra.config.jwt;

import backend.refree.infra.exception.MemberException;
import backend.refree.infra.response.SingleResponse;
import backend.refree.module.Member.MemberLoginDto;
import backend.refree.module.Member.MemberRepository;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("로그인 시도: JwtAuthenticationFilter.attemptAuthentication");

        try{
            MemberLoginDto login = objectMapper.readValue(request.getInputStream(), MemberLoginDto.class);

            // 회원 유효성 검증
            if (!jwtTokenProvider.existByEmail(login.getEmail())) {
                throw new MemberException("존재하지 않는 회원");
            }
            if (!jwtTokenProvider.verifyPassword(login.getPassword(), login.getEmail())) {
                throw new MemberException("비밀번호가 일치하지 않습니다.");
            }

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException {

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        jwtTokenProvider.createTokenAndAddHeader(principalDetails.getMember(), response);

        response.setContentType("application/json");
        SingleResponse singleResponse = new SingleResponse(200, "Login Complete!");
        String result = objectMapper.writeValueAsString(singleResponse);
        response.getWriter().write(result);
    }
}
