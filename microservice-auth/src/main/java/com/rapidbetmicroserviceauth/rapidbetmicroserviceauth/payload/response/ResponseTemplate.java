package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTemplate<T> {
    String status;
    String message;
    T data;
    public ResponseTemplate(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
