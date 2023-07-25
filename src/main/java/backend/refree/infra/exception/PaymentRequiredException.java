package backend.refree.infra.exception;

public class PaymentRequiredException extends IllegalArgumentException { // 402에러

    public PaymentRequiredException(String message) {
        super(message);
    }
}
