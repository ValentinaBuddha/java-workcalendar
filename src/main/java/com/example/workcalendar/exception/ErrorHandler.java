package com.example.workcalendar.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
            WrongDatesException.class, MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validateException(RuntimeException e) {
        log.info(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse entityNotFoundException(EntityNotFoundException e) {
        log.info(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({ParticipantIsInitiatorException.class, DataIntegrityViolationException.class,
            RequestAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictException(RuntimeException e) {
        log.info(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse notInitiatorException(final NotInitiatorException e) {
        log.info(e.getMessage());
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error(e.getMessage());
        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }
}
