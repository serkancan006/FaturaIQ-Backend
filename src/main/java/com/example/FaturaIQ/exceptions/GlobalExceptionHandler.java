package com.example.FaturaIQ.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ErrorDTO createErrorDTO(HttpStatus status, String message, WebRequest request) {
        ErrorDTO error = new ErrorDTO();
        error.setTimestamp(new Date());
        error.setStatus(status.value());
        error.addError("error", message);
        error.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handleGenericException(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Beklenmeyen bir hata oluştu.", request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleCompanyNotFoundException(CompanyNotFoundException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleInvalidCredentialsException(InvalidCredentialsException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleUserException(UserException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(RoleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handleRoleException(RoleException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(JwtValidationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorDTO handleJwtValidationException(JwtValidationException ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler({AuthorizationDeniedException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorDTO handleAuthorizationDeniedException(Exception ex, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        return createErrorDTO(HttpStatus.FORBIDDEN, "Bu kaynağa erişmek için gerekli izinlere sahip değilsiniz.", request);
    }

    // ResourceNotFoundException ekleyebilirisin

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LOGGER.error(ex.getMessage(), ex);
        ErrorDTO error = new ErrorDTO();
        error.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        error.setStatus(status.value());
        error.setTimestamp(new Date());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError ->
                error.addError(fieldError.getField() , fieldError.getDefaultMessage())
        );

        return new ResponseEntity<>(error, headers, status);
    }
}