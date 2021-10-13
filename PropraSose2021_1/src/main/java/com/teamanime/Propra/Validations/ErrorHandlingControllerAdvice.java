package com.teamanime.Propra.Validations;




import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.teamanime.Propra.Exceptions.PropraException;

@ControllerAdvice
@RequestMapping("/*")
public class ErrorHandlingControllerAdvice {
	

	 

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  
  ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
      error.getViolations().add(new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
    }
    return error;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  
  ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    ValidationErrorResponse error = new ValidationErrorResponse();
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      error.getViolations().add(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return error;
  }
  
  

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String exception(final Throwable throwable, final Model model) {
      //logger.error("Exception during execution of SpringSecurity application", throwable);
      String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
      model.addAttribute("errorMessage", errorMessage);
      return "Pages/error";
  }


  
}
