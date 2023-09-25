package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.services;

import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.model.User;
import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User newUser(String email, String password){
        try{
            User newUser = new User(email,password);
            User user = this.userRepository.save(newUser);
            return user;
        }catch(Throwable err){
            return null;
        }
    }

    public Long getEmailID(String email){
        try{
            return this.userRepository.findByEmail(email).getId();
        }catch(Throwable err){
            return null;
        }
    }

    public Boolean verifyLogin(String email, String password){
        try{
            User user = this.userRepository.findByEmail(email);
            if(user.getPassword().equals(password)){
                return true;
            }else{
                return null;
            }
        }catch(Throwable err){
            return null;
        }
    }

    public Boolean verifyAdmin(Long userid){
        try{
            User user = this.userRepository.findById(userid).orElse(null);
            if(user.getAdmin() == true){
                return true;
            }else{
                return null;
            }
        }catch(Throwable err){
            return null;
        }
    }

    public Float getUserCredit(Long userid){
        try{
            User user = this.userRepository.findById(userid).orElse(null);
            if (user != null) {
                return user.getCredit();
            }else{
                return null;
            }
        }catch(Throwable err){
            return null;
        }
    }

    public void incrementUserCredit(Long userid, Float amount){

    }

    public void decreaseUserCredit(Long userid, Float amount){

    }
}
