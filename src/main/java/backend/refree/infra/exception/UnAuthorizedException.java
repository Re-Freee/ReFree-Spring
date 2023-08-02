package backend.refree.infra.exception;

public class UnAuthorizedException extends IllegalArgumentException { // 401에러

    public UnAuthorizedException(String message) {
        super(message);
    }
}
