package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.controllers;

import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.model.User;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.DTO.AuthorizeJWTDTO;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.DTO.LoginDTO;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response.ResponseAuthorizeJWT;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response.ResponseLogin;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.payload.response.ResponseTemplate;
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
                        .body(new ResponseTemplate<ResponseLogin>(
                                "succes",
                                "user creted succefully",
                                new ResponseLogin( newUser,token)
                        ));
            }else{
                return ResponseEntity.badRequest()
                        .body(new ResponseTemplate<ResponseLogin>(
                                "fail",
                                "user was already created",
                                new ResponseLogin( null, null)
                        ));
            }

        }catch(Throwable err){
            return ResponseEntity.badRequest()
                    .body(new ResponseTemplate<ResponseLogin>(
                            "fail",
                            "internal error occured creating the user",
                            new ResponseLogin( null, null)
                    ));
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
                        .body(new ResponseTemplate<ResponseLogin>(
                                "succes",
                                "user logged succefully",
                                new ResponseLogin( null,token)
                        ));

            }else{
                return ResponseEntity.badRequest()
                        .body(new ResponseTemplate<ResponseLogin>(
                        "fail",
                        "user credentials dont match",
                        new ResponseLogin( null, null)
                ));
            }

        }catch(Throwable err){
            return ResponseEntity.badRequest()
//                    .body(new ResponseLogin("fail","error occured login the user", null,null));
                    .body(new ResponseTemplate<ResponseLogin>(
                            "succes",
                            "internal error ocurred login the user",
                            new ResponseLogin( null, null)
                    ));
        }
    }

    private String createAuthCookie(Long id){
        //Generate JWT
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken(id);
        System.out.println("jwt " + token);
        return token;
    }


    // endpoints used by other microservices

    @GetMapping("/authorized/jwt")
    public ResponseEntity authorizeJwt(@RequestBody AuthorizeJWTDTO body) {
        try{
            Boolean isAuthorized;

            isAuthorized = this.jwtUtil.authorize(body.getJwt());

            return ResponseEntity.ok()
                    .body(new ResponseTemplate<ResponseAuthorizeJWT>(
                            "succes",
                            "",
                            new ResponseAuthorizeJWT(isAuthorized)
                    ));

        }catch(Throwable err){
            return ResponseEntity.badRequest()
                    .body(new ResponseTemplate<ResponseAuthorizeJWT>(
                            "succes",
                            "internal error occurred",
                            new ResponseAuthorizeJWT(false)
                    ));
        }
    }

    @GetMapping("/authorized/admin/jwt")
    public ResponseEntity authorizeAdminJwt(@RequestBody AuthorizeJWTDTO body) {
        try{
            Boolean isAuthorized = false;

            Long userid = this.jwtUtil.extractUserId(body.getJwt());

            isAuthorized = this.userService.verifyAdmin(userid);

            return ResponseEntity.ok()
                    .body(new ResponseTemplate<ResponseAuthorizeJWT>(
                            "succes",
                            "",
                            new ResponseAuthorizeJWT(isAuthorized)
                    ));

        }catch(Throwable err){
            return ResponseEntity.badRequest()
                    .body(new ResponseTemplate<ResponseAuthorizeJWT>(
                            "succes",
                            "internal error occurred",
                            new ResponseAuthorizeJWT(false)
                    ));
        }
    }

}
