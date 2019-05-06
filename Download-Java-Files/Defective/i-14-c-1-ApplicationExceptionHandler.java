package br.com.uds.udspizzaria.application.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.uds.udspizzaria.application.service.exception.AdicionalNaoEncontradoException;
import br.com.uds.udspizzaria.application.service.exception.InformacaoNaoEncontradaException;
import br.com.uds.udspizzaria.application.service.exception.PedidoNaoEncontradoException;
import br.com.uds.udspizzaria.application.service.exception.SaborNaoEncontradoException;
import br.com.uds.udspizzaria.application.service.exception.TamanhoNaoEncontradoException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    protected MessageSource messageSource;
    
    @ExceptionHandler({ InformacaoNaoEncontradaException.class })
    public ResponseEntity<Object> handleInformacaoNaoEncontradaException(InformacaoNaoEncontradaException ex,
            WebRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND, request, "recurso.informacao-nao-encontrada");
    }
    
    @ExceptionHandler({ TamanhoNaoEncontradoException.class })
    public ResponseEntity<Object> handleTamanhoNaoEncontradoException(TamanhoNaoEncontradoException ex,
            WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST, request, "recurso.tamanho.inexistente");
    }
    
    @ExceptionHandler({ AdicionalNaoEncontradoException.class })
    public ResponseEntity<Object> handleAdicionalNaoEncontradoException(AdicionalNaoEncontradoException ex,
            WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST, request, "recurso.adicional.inexistente");
    }
    
    @ExceptionHandler({ SaborNaoEncontradoException.class })
    public ResponseEntity<Object> handleSaborNaoEncontradoException(SaborNaoEncontradoException ex,
            WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST, request, "recurso.sabor.inexistente");
    }
    
    @ExceptionHandler({ PedidoNaoEncontradoException.class })
    public ResponseEntity<Object> handlePedidoNaoEncontradoException(PedidoNaoEncontradoException ex,
            WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST, request, "recurso.pedido.inexistente");
    }
    
    protected ResponseEntity<Object> handleException(Exception ex, HttpStatus httpStatus, WebRequest request,
            String sourceMessage) {
        String mensagem = messageSource.getMessage(sourceMessage, null, LocaleContextHolder.getLocale());

        return handleExceptionInternal(ex, Arrays.asList(mensagem), new HttpHeaders(), httpStatus, request);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> erros = obterListaErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }
    
    protected List<String> obterListaErros(BindingResult bindingResult) {
        List<String> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            erros.add(this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
        }

        return erros;
    }
}
