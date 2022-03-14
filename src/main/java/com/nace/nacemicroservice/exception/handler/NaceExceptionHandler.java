package com.nace.nacemicroservice.exception.handler;

import com.nace.nacemicroservice.constants.NaceEnum;
import com.nace.nacemicroservice.dto.ErrorResponseDto;
import com.nace.nacemicroservice.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fozia
 */
@ControllerAdvice
@ResponseBody
public class NaceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ErrorResponseDto> handleConstraintViolation(ConstraintViolationException ex, WebRequest request){
        List<String>  details =  ex.getConstraintViolations().parallelStream().map(e->e.getMessage()).collect(Collectors.toList());

    ErrorResponseDto error = new ErrorResponseDto(NaceEnum.Errors.BAD_REQUEST.getValue(),details, LocalDateTime.now());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex,WebRequest request){

        ErrorResponseDto error = new ErrorResponseDto(NaceEnum.Errors.NO_RECORD_FOUND.getValue(),
                Arrays.asList(NaceEnum.Errors.NO_RECORD_FOUND_MSG.getValue()),LocalDateTime.now() );
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponseDto> handleExceptions(Exception ex,WebRequest request){

        ErrorResponseDto error=new ErrorResponseDto(NaceEnum.Errors.INTERNAL_SERVER.getValue(),
                Arrays.asList(NaceEnum.Errors.INTERNAL_SERVER_ERROR_MSG.getValue()),LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NumberFormatException.class)
    public final ResponseEntity<ErrorResponseDto> handleNumberFormatException(NumberFormatException ex, WebRequest request){
        ErrorResponseDto error = new ErrorResponseDto(NaceEnum.Errors.INTERNAL_SERVER.getValue(),
                Arrays.asList(NaceEnum.Errors.INTERNAL_SERVER_ERROR_MSG.getValue()),LocalDateTime.now());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ErrorResponseDto> handleIOException(IOException ex,WebRequest request){
        ErrorResponseDto error = new ErrorResponseDto(NaceEnum.Errors.INTERNAL_SERVER.getValue(),
                Arrays.asList(NaceEnum.Errors.INTERNAL_SERVER_ERROR_MSG.getValue()),LocalDateTime.now());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
        @ExceptionHandler(IllegalStateException.class)
        public final ResponseEntity<ErrorResponseDto> handleIllegalStateException(IllegalStateException ex,WebRequest request){
            ErrorResponseDto error = new ErrorResponseDto(NaceEnum.Errors.INTERNAL_SERVER.getValue(),
                    Arrays.asList(NaceEnum.Errors.INTERNAL_SERVER_ERROR_MSG.getValue()),LocalDateTime.now());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
