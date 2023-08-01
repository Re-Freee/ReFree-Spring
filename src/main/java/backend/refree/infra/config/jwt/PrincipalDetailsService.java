package backend.refree.infra.config.jwt;

import backend.refree.infra.exception.MemberException;
import backend.refree.module.Member.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        memberEntity member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new MemberException("존재하지 않는 계정입니다."));

        return new PrincipalDetails(member);
    }

}
