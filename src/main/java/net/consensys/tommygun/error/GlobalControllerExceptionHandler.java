package net.consensys.tommygun.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
  @ExceptionHandler(TaskNotFound.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public Map<String, String> handleTaskNotFound(final TaskNotFound ex) {
    final Map<String, String> errors = new HashMap<>();
    errors.put("errorMessage", ex.getMessage());
    return errors;
  }

  @ExceptionHandler(EthereumNodeUnreachable.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public Map<String, String> handleEthereumNodeUnreachable(final EthereumNodeUnreachable ex) {
    final Map<String, String> errors = new HashMap<>();
    errors.put("errorMessage", ex.getMessage());
    return errors;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(final MethodArgumentNotValidException ex) {
    final Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return errors;
  }
}
