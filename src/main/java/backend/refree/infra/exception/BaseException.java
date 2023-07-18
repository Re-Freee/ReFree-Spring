package backend.refree.infra.exception;

public abstract class BaseException extends RuntimeException {
    public abstract BaseExceptionType getExceptionType();
}

// 앞으로 정의할 모든 커스텀 예외의 부모 클래스