package com.thuc.accounts.exception;

import com.thuc.accounts.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(
            CustomerAlreadyExistsException e, WebRequest request) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorMessage(e.getMessage())
                .errorTime(LocalDateTime.now())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .apiPath(request.getDescription(false).replace("uri=","")) // khong lay ip chi lay api
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }
    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<?> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException e, WebRequest request) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorMessage(e.getMessage())
                .errorTime(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .apiPath(request.getDescription(false).replace("uri=","")) // khong lay ip chi lay api
                .errorCode(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(
            ResourceNotFoundException e, WebRequest request
    ){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorMessage(e.getMessage())
                .errorTime(LocalDateTime.now())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .apiPath(request.getDescription(false).replace("uri=",""))
                .errorCode(HttpStatus.NOT_FOUND)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> validationErrors=new HashMap<>();
        List<ObjectError> objectErrorList=ex.getBindingResult().getAllErrors();
        objectErrorList.forEach(error -> {
            String fieldName=((FieldError)error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName,validationMsg);
        });
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorMessage(ex.getMessage())
                .errorTime(LocalDateTime.now())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .apiPath(request.getDescription(false).replace("uri=",""))
                .errorCode(HttpStatus.BAD_REQUEST)
                .details(validationErrors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    };
}
