package com.rezolve.test;

import com.google.gson.JsonObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        JsonObject message = new JsonObject();

        if (!bindingResult.getFieldErrors().isEmpty()) {
            FieldError msg = bindingResult.getFieldErrors().get(0);
            message.addProperty("message", msg == null ? "Error in field " + msg.getField() : msg.getDefaultMessage());
            return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(message.toString());
        }

        logger.error("ERROR: Controller Advice", ex);
        message.addProperty("message", ex.toString() + "\n" + ex.getMessage());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(message.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {

        JsonObject data = new JsonObject();
        data.addProperty("error", ex.getMessage());

        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON).body(data.toString());
    }

    @Override
    public ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(status).body(ex);
    }
}