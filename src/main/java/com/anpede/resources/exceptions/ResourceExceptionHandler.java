package com.anpede.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.anpede.services.exceptions.EntityNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice //É um componente que vai junto na hora de subir. Basta fazer a notação.
public class ResourceExceptionHandler {
	
	@ExceptionHandler(EntityNotFoundException.class)
	//Especificando a classe que resolve
	public ResponseEntity<StandardError> entityNotFound(
			EntityNotFoundException e, 
			HttpServletRequest request){
		
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		//Pega o valor para esse tipo de erro (404)
		error.setError("Recurso não encontrado");
		error.setMessage(e.getMessage());
		error.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);		
	}

}
