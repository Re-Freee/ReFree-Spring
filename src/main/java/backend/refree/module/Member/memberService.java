package backend.refree.module.Member;

import backend.refree.infra.config.jwt.JwtTokenProvider;
import backend.refree.infra.exception.MemberException;
import backend.refree.infra.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static backend.refree.module.Analysis.KomoranUtils.log;

@Service
@RequiredArgsConstructor
public class memberService {

    private final MemberRepository MemberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    SingleResponse signup(MemberSignupDto memberSignupDto) {
        memberEntity member = memberSignupDto.toEntity();

        // 이미 존재하는 이메일
        if (MemberRepository.findByEmail(memberSignupDto.getEmail()).isPresent()){
            throw new MemberException("이미 존재하는 계정입니다.");
        }

        // 비밀번호 일치하지 않음
        if (!memberSignupDto.getPassword().equals(memberSignupDto.getCheckPassword())) {
            throw new MemberException("비밀번호가 일치하지 않습니다.");
        }

        SingleResponse singleResponse = new SingleResponse(201, "Registeration Complete!");

        member.encodePassword(passwordEncoder);
        member.encodeCheckPassword(passwordEncoder);

        MemberRepository.save(member);

        return singleResponse;
    }

    SingleResponse login(MemberLoginDto memberLoginDto, HttpServletResponse response) {
        memberEntity member = MemberRepository.findByEmail(memberLoginDto.getEmail())
                .orElseThrow(() -> new MemberException("존재하지 않는 계정입니다.")); // 존재하지 않는 계정
        if (!passwordEncoder.matches(memberLoginDto.getPassword(), member.getPassword())){
            throw new MemberException("비밀번호가 일치하지 않습니다."); // 일치하지 않는 비밀번호
        }

        SingleResponse singleResponse = new SingleResponse(200, "Login Complete!");

        String token = jwtTokenProvider.createToken(member.getEmail()); // 토큰 발급
        response.setHeader("Authorization", "Bearer " + token); // response.header

        return singleResponse;
    }

    SingleResponse check(String email) {
        memberEntity member = MemberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException("회원 정보를 찾을 수 없습니다."));

        SingleResponse singleResponse = new SingleResponse(200, member.getEmail());
        return singleResponse;
    }
}
