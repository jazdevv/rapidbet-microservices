package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeJWTDTO {
    String jwt;
}
