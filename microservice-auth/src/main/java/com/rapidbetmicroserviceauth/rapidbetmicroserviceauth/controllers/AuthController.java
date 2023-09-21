package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.controllers;

import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.model.User;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.DTO.LoginDTO;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response.ResponseLogin;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.services.UserService;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    public AuthController() {
        this.jwtUtil = new JwtUtil();
    }
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody LoginDTO body){
        try{
            //check user exists
            Long userExists = this.userService.getEmailID(body.getEmail());

            //create if not exists else dont
            if(userExists==null){
                User newUser = this.userService.newUser(body.getEmail(), body.getPassword());
                //generate cookies
                String token = this.createAuthCookie(newUser.getId());
                //Create the cookie
                ResponseCookie cookie = ResponseCookie.from("jwtbet",token).build();
                // Add the cookie to the response headers
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

                return ResponseEntity.ok()
                        .headers(responseHeaders)
                        .body(new ResponseLogin("succes","user creted succefully", newUser,token));

            }else{
                return ResponseEntity.badRequest()
                        .body(new ResponseLogin("fail","user was already created", null,null));
            }

        }catch(Throwable err){
            return ResponseEntity.badRequest()
                    .body(new ResponseLogin("fail","error occured creating the user", null,null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO body){
        try{
            //check user credentials are valid
            Boolean areCredentialsValid = this.userService.verifyLogin(body.getEmail(),body.getPassword());

            if(areCredentialsValid==true){
                //get user id
                Long userId = this.userService.getEmailID(body.getEmail());
                //generate cookies
                String token = this.createAuthCookie(userId);
                //Create the cookie
                ResponseCookie cookie = ResponseCookie.from("jwtbet",token).build();
                // Add the cookie to the response headers
                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());

                return ResponseEntity.ok()
                        .headers(responseHeaders)
                        .body(new ResponseLogin("succes","user logged succefully", null, token));

            }else{
                return ResponseEntity.badRequest()
                        .body(new ResponseLogin("fail","user credentials dont match", null,null));
            }

        }catch(Throwable err){
            return ResponseEntity.badRequest()
                    .body(new ResponseLogin("fail","error occured login the user", null,null));
        }
    }

    private String createAuthCookie(Long id){
        //Generate JWT
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(id);
        System.out.println("jwt " + token);
        return token;
    }

//
//    // endpoints used by other microservices
//
//    @GetMapping("/authorized/jwt")
//    public ResponseEntity authorizeJwt() {
//
//    }

    // increase and decrease user credit should be managed with rabbitmq over http


}
