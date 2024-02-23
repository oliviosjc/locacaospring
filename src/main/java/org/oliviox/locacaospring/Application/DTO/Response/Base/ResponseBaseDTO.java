package org.oliviox.locacaospring.Application.DTO.Response.Base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class ResponseBaseDTO<T>
{
    public String message;

    public HttpStatus statusCode;

    public List<String> errors;

    public T data;

    public ResponseBaseDTO(String message, HttpStatus statusCode, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public ResponseBaseDTO(String message, HttpStatus statusCode)
    {
        this.message = message;
        this.statusCode = statusCode;
        this.data = null;
    }


    public ResponseBaseDTO()
    {

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setData(T data) {
        this.data = data;
    }
}
