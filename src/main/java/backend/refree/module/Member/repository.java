package backend.refree.module.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface repository extends JpaRepository <memberEntity, String> {
    Optional<memberEntity> findByEmail(String email);
}
