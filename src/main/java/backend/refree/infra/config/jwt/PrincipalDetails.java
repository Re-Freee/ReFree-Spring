package backend.refree.infra.config.jwt;

import backend.refree.module.Member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {
    private final Member member;

    @Override
    public String getUsername(){
        return member.getEmail();
    }

    @Override
    public String getPassword(){
        return member.getPassword();
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    @Override
    public boolean isAccountNonExpired(){ return true; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return null;
    }

}
