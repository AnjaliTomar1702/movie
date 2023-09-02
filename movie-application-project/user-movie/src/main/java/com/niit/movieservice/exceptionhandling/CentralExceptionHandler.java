package com.niit.movieservice.exceptionhandling;

import com.niit.movieservice.dto.ErrorMessageDTO;
import com.niit.movieservice.dto.MessageDTO;
import com.niit.movieservice.exception.*;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@RestControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessageDTO> handleUnauthorizedException(InvalidCredentialsException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ErrorMessageDTO> handleIOException(IOException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = TokenExpiredException.class)
    public ResponseEntity<ErrorMessageDTO> handleTokenExpiredException(TokenExpiredException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public ResponseEntity<ErrorMessageDTO> handleMalformedJwtException(MalformedJwtException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserNotFoundException(UserNotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ErrorMessageDTO> handleNullPointerException(NullPointerException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = MovieAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageDTO> handleMovieAlreadyExistsException(MovieAlreadyExistsException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = MovieNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> handleMovieNotFoundException(MovieNotFoundException e) {
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleException(Exception e) throws Exception {
        if(e instanceof InvalidCredentialsException
                || e instanceof IOException
                || e instanceof TokenExpiredException
                || e instanceof MalformedJwtException
                || e instanceof UserNotFoundException
                || e instanceof NullPointerException
                || e instanceof UserAlreadyExistsException
                || e instanceof MovieAlreadyExistsException
                || e instanceof MovieNotFoundException
        )
            throw e;
        e.printStackTrace();
        return new ResponseEntity<>(new ErrorMessageDTO("Server error, please try in some time"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
