package co.com.bb.kata.api.config;

import co.com.bb.kata.model.exception.BusinessException;
import co.com.bb.kata.model.exception.TechnicalException;
import co.com.bb.kata.model.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("type", "BUSINESS_ERROR");
        errorBody.put("code", ex.getCode());
        errorBody.put("message", ex.getMessage());
        errorBody.put("description", ex.getDescription());
        errorBody.put("itcCode", ex.getItcCode());

        HttpStatus status = mapHttpStatus(ex.getItcCode());
        return new ResponseEntity<>(errorBody, status);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<Object> handleTechnicalException(TechnicalException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("type", "TECHNICAL_ERROR");
        errorBody.put("code", ex.getCode());
        errorBody.put("message", ex.getMessage());
        errorBody.put("description", ex.getDescription());
        errorBody.put("itcCode", ex.getItcCode());

        HttpStatus status = mapHttpStatus(ex.getItcCode());
        return new ResponseEntity<>(errorBody, status);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("type", "VALIDATION_ERROR");
        errorBody.put("code", ex.getCode());
        errorBody.put("message", ex.getMessage());
        errorBody.put("itcCode", ex.getItcCode());

        HttpStatus status = mapHttpStatus(ex.getItcCode());
        return new ResponseEntity<>(errorBody, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleSpringValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("type", "VALIDATION_ERROR");
        errorBody.put("code", "VAL9999");
        errorBody.put("message", "Request validation failed");
        errorBody.put("details", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList());

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("type", "UNEXPECTED_ERROR");
        errorBody.put("code", "GEN0001");
        errorBody.put("message", "An unexpected error occurred");
        errorBody.put("details", ex.getMessage());

        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus mapHttpStatus(String itcCode) {
        if (itcCode == null) return HttpStatus.INTERNAL_SERVER_ERROR;

        return switch (itcCode) {
            case "400" -> HttpStatus.BAD_REQUEST;
            case "401" -> HttpStatus.UNAUTHORIZED;
            case "403" -> HttpStatus.FORBIDDEN;
            case "404" -> HttpStatus.NOT_FOUND;
            case "409" -> HttpStatus.CONFLICT;
            case "500" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}

