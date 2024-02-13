package org.oliviox.locacaospring.Application.DTO.Response;


import org.springframework.http.HttpStatus;

import java.util.List;

public class ResponseDTO<T>
{
    public String message;

    public HttpStatus statusCode;

    public List<String> errors;

    public T data;

    public ResponseDTO(String message, HttpStatus statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseDTO(String message, HttpStatus statusCode)
    {
        this.message = message;
        this.statusCode = statusCode;
        this.data = null;
    }


    public ResponseDTO()
    {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}