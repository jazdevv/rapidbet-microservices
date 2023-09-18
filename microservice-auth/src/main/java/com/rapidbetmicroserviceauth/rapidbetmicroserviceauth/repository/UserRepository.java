package com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.repository;

import com.rapidbetmicroserviceauth.rapidbetmicroserviceauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
