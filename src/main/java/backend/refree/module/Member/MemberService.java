package backend.refree.module.Member;

import backend.refree.infra.config.jwt.JwtAuthenticationFilter;
import backend.refree.infra.config.jwt.JwtTokenProvider;
import backend.refree.infra.config.jwt.PrincipalDetails;
import backend.refree.infra.exception.MemberException;
import backend.refree.infra.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EntityManager en;

    // 회원가입
    SingleResponse signup(MemberSignupDto memberSignupDto) {
        memberEntity member = memberSignupDto.toEntity();

        // 이미 존재하는 이메일
        if (memberRepository.findByEmail(memberSignupDto.getEmail()).isPresent()){
            throw new MemberException("이미 존재하는 계정입니다.");
        }

        // 비밀번호 일치하지 않음
        if (!memberSignupDto.getPassword().equals(memberSignupDto.getCheckPassword())) {
            throw new MemberException("비밀번호가 일치하지 않습니다.");
        }

        SingleResponse singleResponse = new SingleResponse(201, "Registeration Complete!");

        member.encodePassword(passwordEncoder);
        member.encodeCheckPassword(passwordEncoder);

        memberRepository.save(member);

        return singleResponse;
    }

    SingleResponse search(MemberPwSearchDto memberPwSearchDto) {
        memberEntity member = memberPwSearchDto.toEntity();
        if (memberRepository.findByEmail(memberPwSearchDto.getEmail()).isPresent() == false){
            throw new MemberException("존재하지 않는 계정입니다.");
        }

        SingleResponse singleResponse = new SingleResponse(200, "EMAIL_EXIST");
        return singleResponse;
    }

    SingleResponse modify(String email, MemberPwModifyDto memberPwModifyDto) {
        memberEntity member = memberPwModifyDto.toEntity();

        if (memberRepository.findByEmail(email).isPresent() ==  false || email == null){ // Exception (이미 존재하는 것이 확인된 계정에서만 호출되는 API
            throw new MemberException("잘못된 접근입니다.");
        }

        if (!memberPwModifyDto.getNewPassword().equals(memberPwModifyDto.getCheckNewPassword())) {
            throw new MemberException("비밀번호가 일치하지 않습니다.");
        }

        Optional<memberEntity> updateMember = memberRepository.findByEmail(email);
        member = updateMember.get();

        // 비밀번호 변경
        member.updatePassword(memberPwModifyDto.getNewPassword());
        member.updateCheckPassword(memberPwModifyDto.getCheckNewPassword());

        System.out.println(memberPwModifyDto.getNewPassword());
        System.out.println(member.getPassword());

        // password Encoding
        member.encodePassword(passwordEncoder);
        member.encodeCheckPassword(passwordEncoder);

        memberRepository.save(member);

        SingleResponse singleResponse = new SingleResponse(200, "PASSWORD_CHANGE");
        return singleResponse;
    }

    SingleResponse check(String email) {
        memberEntity member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("회원 정보를 찾을 수 없습니다."));

        SingleResponse singleResponse = new SingleResponse(200, member.getEmail());
        return singleResponse;
    }
}
