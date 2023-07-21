package backend.refree.infra.exception2;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    int getErrorCode();
    HttpStatus getHttpStatus();
    String getErrorMessage();
}
