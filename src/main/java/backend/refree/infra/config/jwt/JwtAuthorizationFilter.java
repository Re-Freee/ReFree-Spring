package backend.refree.infra.config.jwt;

import backend.refree.module.Member.Member;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옴
        String token = jwtTokenProvider.resolveToken(request);
        // 유효한 토큰인지 확인
        Member member = jwtTokenProvider.validateTokenAndFindMember(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new PrincipalDetails(member),
                null
        );
        // SecurityContext 에 Authentication 객체를 저장한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
