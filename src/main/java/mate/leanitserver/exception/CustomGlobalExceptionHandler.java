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
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_OCCURRENCE_TIME, LocalDateTime.now());
        body.put(ERROR_STATUS, HttpStatus.NOT_FOUND.value());
        body.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(AnkiConnectException.class)
    public ResponseEntity<Object> handleAnkiConnectException(AnkiConnectException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_OCCURRENCE_TIME, LocalDateTime.now());
        body.put(ERROR_STATUS, HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<Object> handleLoginException(LoginException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(ERROR_OCCURRENCE_TIME, LocalDateTime.now());
        body.put(ERROR_STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ERROR_MESSAGE, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
