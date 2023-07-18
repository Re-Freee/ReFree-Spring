package backend.refree.infra.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import backend.refree.module.Member.MemberExceptionType;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {


    @ExceptionHandler(BaseException.class)
    public ResponseEntity handleBaseEx(BaseException exception){
        // 콘솔
        log.error("BaseException errorMessage(): {}",exception.getExceptionType().getErrorMessage());
        log.error("BaseException errorCode(): {}",exception.getExceptionType().getErrorCode());

        // Response-Body
        return new ResponseEntity(new ExceptionDto(exception.getExceptionType().getErrorCode(), exception.getExceptionType().getErrorMessage()),
                exception.getExceptionType().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleMemberEx(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity(HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgEx(MethodArgumentNotValidException exception){
        ExceptionDto exceptionDto = makeErrorResponse(exception.getBindingResult());
        return exceptionDto;
    }

    @Data
    @AllArgsConstructor
    static class ExceptionDto {
        private Integer errorCode;
        private String errorMessage;
    }

    private ExceptionDto makeErrorResponse(BindingResult bindingResult){

        int errorCode = 0;
        String errorMessage = "";

        if (bindingResult.hasErrors()){

            String bindResultCode = bindingResult.getFieldError().getCode();
            errorMessage = bindingResult.getFieldError().getDefaultMessage();

            if (bindResultCode.equals("NotBlank"))
                errorCode = MemberExceptionType.NOT_NULL.getErrorCode();
            else if (bindingResult.getFieldError().getField().equals("password"))
                errorCode = MemberExceptionType.WRONG_PASSWORD.getErrorCode();
            else if (bindingResult.getFieldError().getField().equals("nickname"))
                errorCode = MemberExceptionType.WRONG_NICKNAME.getErrorCode();
            else if (bindResultCode.equals("Email"))
                errorCode = MemberExceptionType.WRONG_EMAIL.getErrorCode();
        }

        return new ExceptionDto(errorCode, errorMessage);
    }
}