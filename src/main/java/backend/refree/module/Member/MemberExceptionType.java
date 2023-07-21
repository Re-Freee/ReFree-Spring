package backend.refree.module.Member;

import backend.refree.infra.exception2.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {
    // 회원가입
    ALREADY_EXIST_EMAIL(600, HttpStatus.OK, "이미 존재하는 계정입니다."),
    WRONG_NICKNAME(601, HttpStatus.OK, "닉네임은 2글자 이상, 8글자 이하여야 합니다."),
    WRONG_PASSWORD(602, HttpStatus.OK, "비밀번호는 8자 이상이어야 합니다."),
    NOT_MATCH_PASSWORD(603, HttpStatus.OK, "비밀번호가 일치하지 않습니다."),
    NOT_NULL(604, HttpStatus.OK, "필수 입력 사항입니다."),
    //로그인
    NOT_MATCH_MEMBER(605, HttpStatus.OK, "존재하지 않는 계정입니다."),
    WRONG_EMAIL(606, HttpStatus.OK, "이메일 형식으로 입력해 주세요.");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage){
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
    @Override
    public int getErrorCode(){
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage(){
        return this.errorMessage;
    }
}
