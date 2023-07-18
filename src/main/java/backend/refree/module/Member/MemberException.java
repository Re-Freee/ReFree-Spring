package backend.refree.module.Member;

import backend.refree.infra.exception.BaseException;
import backend.refree.infra.exception.BaseExceptionType;

public class MemberException extends BaseException {
    private BaseExceptionType exceptionType;

    public MemberException(BaseExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType(){
        return exceptionType;
    }
}
