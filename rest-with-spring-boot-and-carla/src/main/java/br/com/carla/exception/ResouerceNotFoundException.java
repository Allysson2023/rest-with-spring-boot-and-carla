package br.com.carla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResouerceNotFoundException extends RuntimeException {

    public ResouerceNotFoundException(String message) {
        super(message);
    }
}
