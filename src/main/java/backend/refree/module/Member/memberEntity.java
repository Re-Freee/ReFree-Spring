package backend.refree.module.Member;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity
@Builder
@Table(name = "Member")
@DynamicUpdate
public class memberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private int id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "check_password", nullable = false)
    private String checkPassword;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", length = 8, nullable = false)
    private String nickname;

    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    public void encodeCheckPassword(PasswordEncoder passwordEncoder){
        this.checkPassword = passwordEncoder.encode(checkPassword);
    }

    public void updatePassword(String newPassword){
        this.password = newPassword;
    }

    public void updateCheckPassword(String newCheckPassword){
        this.checkPassword = newCheckPassword;
    }
}