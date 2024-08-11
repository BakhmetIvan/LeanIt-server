package mate.leanitserver.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ERROR_OCCURRENCE_TIME = "timestamp";
    private static final String ERROR_STATUS = "status";
    private static final String ERRORS_LIST = "errors";
    private static final String ERROR_MESSAGE = "message";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_OCCURRENCE_TIME, LocalDateTime.now());
        body.put(ERROR_STATUS, HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(this::getErrorMessage)
                .toList();
        body.put(ERRORS_LIST, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    private String getErrorMessage(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            String message = fieldError.getDefaultMessage();
            return message;
        }
        return error.getDefaultMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return buildError(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AnkiConnectException.class)
    public ResponseEntity<Object> handleAnkiConnectException(AnkiConnectException ex) {
        return buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleLoginException(LoginException ex) {
        return buildError(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Object> handleRegistrationException(RegistrationException ex) {
        return buildError(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handlerInvalidPasswordException(InvalidPasswordException ex) {
        return buildError(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> handlerDuplicateException(DuplicateException ex) {
        return buildError(ex, HttpStatus.CONFLICT);
    }

    private ResponseEntity<Object> buildError(Exception ex, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_OCCURRENCE_TIME, LocalDateTime.now());
        body.put(ERROR_STATUS, status);
        body.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(status).body(body);
    }
}
