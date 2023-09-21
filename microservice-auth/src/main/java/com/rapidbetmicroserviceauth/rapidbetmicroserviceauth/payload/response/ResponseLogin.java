package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response;

import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.model.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseLogin {
    String status;
    String message;
    User data;
    String jwt;
}
