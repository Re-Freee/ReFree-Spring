package backend.refree.infra.exception;

public class ForbiddenException extends IllegalArgumentException { // 403에러

    public ForbiddenException(String message) {
        super(message);
    }
}
