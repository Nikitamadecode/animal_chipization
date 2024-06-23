package me.kiryakov.animal_chips.configuration;

import jakarta.validation.ConstraintViolationException;
import me.kiryakov.animal_chips.exception.DataConflictException;
import me.kiryakov.animal_chips.exception.InaccessibleEntityException;
import me.kiryakov.animal_chips.exception.InvalidDataException;
import me.kiryakov.animal_chips.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

   @ExceptionHandler ({ConstraintViolationException.class})
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
   }

    @ExceptionHandler({InaccessibleEntityException.class})
    public ResponseEntity handleInaccessibleException(InaccessibleEntityException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity InvalidDataException(InvalidDataException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({DataConflictException.class})
    public ResponseEntity DataConfictException(DataConflictException ex) {
        return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
