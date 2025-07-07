package com.VentaCliente.advice;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class ExceptionHandlerApp {

    @ExceptionHandler(MethodArgumentNotValidException.class)
      public Map<String,String> handleException(MethodArgumentNotValidException exception){
          Map<String,String> errorMap = new HashMap<>();
          exception.getBindingResult().getFieldErrors().forEach(error  ->{

              errorMap.put(error.getField(),error.getDefaultMessage());
          });
        return errorMap;
      }
}
