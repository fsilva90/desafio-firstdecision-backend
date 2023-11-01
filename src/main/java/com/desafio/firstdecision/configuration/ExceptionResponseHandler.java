package com.desafio.firstdecision.configuration;


import com.desafio.firstdecision.dto.error.MensagemErro;
import com.desafio.firstdecision.exception.SenhaException;
import com.desafio.firstdecision.exception.UsuarioInexistenteException;
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
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        return new ResponseEntity<>(new MensagemErro(ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioInexistenteException.class)
    public ResponseEntity<MensagemErro> handleUsuarioInexistente(UsuarioInexistenteException ex) {
        return new ResponseEntity<>(new MensagemErro(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SenhaException.class)
    public ResponseEntity<MensagemErro> handleUsuarioSenhaErro(SenhaException ex) {
        return new ResponseEntity<>(new MensagemErro(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
