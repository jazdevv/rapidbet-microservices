package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseAuthorizeJWT {
    Boolean authorized;
}
