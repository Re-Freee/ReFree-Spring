package backend.refree.module.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository <Member, String> {
    Optional<Member> findByEmail(String email);

    Boolean existsMemberByEmail(String email);
}
