package backend.refree.module.Member;

import backend.refree.infra.config.jwtToken.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class service {

    private final repository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    MessageModel signup(MemberSignupDto memberSignupDto) {
        memberEntity member = memberSignupDto.toEntity();

        // 이미 존재하는 이메일
        if (repository.findByEmail(memberSignupDto.email).isPresent()){
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_EMAIL);
        }

        // 비밀번호 일치하지 않음
        if (memberSignupDto.password.equals(memberSignupDto.checkPassword) == false) {
            throw new MemberException(MemberExceptionType.NOT_MATCH_PASSWORD);
        }

        MessageModel messageModel = new MessageModel();
        messageModel.code = 201;
        messageModel.message = "Registeration Complete!";

        member.encodePassword(passwordEncoder);
        member.encodeCheckPassword(passwordEncoder);

        repository.save(member);

        return messageModel;
    }

    MessageModel login(MemberLoginDto memberLoginDto, HttpServletResponse response) {
        memberEntity member = repository.findByEmail(memberLoginDto.email)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_MATCH_MEMBER)); // 존재하지 않는 계정
        if (!passwordEncoder.matches(memberLoginDto.password, member.getPassword())){
            throw new MemberException(MemberExceptionType.NOT_MATCH_PASSWORD); // 일치하지 않는 비밀번호
        }

        MessageModel messageModel = new MessageModel();
        messageModel.code = 200;
        messageModel.message = "Login Complete!";

        String token = jwtTokenProvider.createToken(member.getEmail()); // 토큰 발급
        response.setHeader("Authorization", "Bearer " + token); // response.header

        return messageModel;
    }

}
