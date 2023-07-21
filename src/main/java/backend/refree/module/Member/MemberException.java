package backend.refree.module.Member;

import backend.refree.infra.exception2.BaseException;
import backend.refree.infra.exception2.BaseExceptionType;

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
