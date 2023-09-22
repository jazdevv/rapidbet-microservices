package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response;

import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseLogin {
    User data;
    String jwt;
}
