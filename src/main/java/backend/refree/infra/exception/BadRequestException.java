package backend.refree.infra.exception;

public class BadRequestException extends IllegalArgumentException { // 400에러

    public BadRequestException(String message) {
        super(message);
    }
}
