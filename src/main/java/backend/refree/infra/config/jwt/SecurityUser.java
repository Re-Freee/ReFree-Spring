package backend.refree.infra.config.jwt;

import backend.refree.module.Member.memberEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class SecurityUser extends User {
    private memberEntity member;

    public SecurityUser(memberEntity member){
        super(member.getEmail(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getEmail()));
        this.member = member;
    }

    public memberEntity getMember() {
        return member;
    }
}
