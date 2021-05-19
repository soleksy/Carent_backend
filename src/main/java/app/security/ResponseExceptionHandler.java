package app.security;


import app.exception.ErrorTemplate;
import app.exception.ServerException;
import app.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({ServerException.class})
    public final ResponseEntity<ErrorTemplate> handleAccessDeniedException(ServerException ex, HttpServletRequest request, WebRequest webRequest) {
        ErrorTemplate error = new ErrorTemplate();
        error.setError(ex.getError().toString());
        MessageUtils.setCommonErrorFields(error, request, ex.getMessage());
        return getResponse(ex, error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ErrorTemplate> handleAuthException(AuthenticationException ex, HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        ErrorTemplate error = new ErrorTemplate();
        error.setError(HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase());
        MessageUtils.setCommonErrorFields(error, request, ex.getMessage());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<ErrorTemplate> getResponse(ServerException ex, ErrorTemplate error) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (ex.getError()) {
            case INVALID_LOGIN_OR_PASSWORD:
            case INVALID_ACCESS_TOKEN:
            case ACCESS_TOKEN_EXPIRED:
                httpStatus = HttpStatus.UNAUTHORIZED;
                break;
            case PASSWORDS_DONT_MATCH:
            case EMAIL_NOT_UNIQUE:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case INSUFFICIENT_PRIVILEGES:
                httpStatus = HttpStatus.FORBIDDEN;
                break;
        }
        error.setStatus(httpStatus.value());
        return new ResponseEntity<>(error, httpStatus);
    }

}
