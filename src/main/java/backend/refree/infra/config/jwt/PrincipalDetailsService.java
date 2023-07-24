package backend.refree.infra.config.jwt;

import backend.refree.module.Member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        memberEntity member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        return new SecurityUser(member);
    }

}
