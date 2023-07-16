package backend.refree.config;

import backend.refree.module.Member.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

@Service
public class SecurityUserDetailService implements UserDetailsService {

    @Autowired
    private repository memberReporitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<memberEntity> optional = memberReporitory.findByEmail(username);
        if(!optional.isPresent()){
            throw new UsernameNotFoundException(username + " 사용자 없음");
        }
        else{
            memberEntity member = optional.get();
            return new SecurityUser(member);
        }
    }

}
