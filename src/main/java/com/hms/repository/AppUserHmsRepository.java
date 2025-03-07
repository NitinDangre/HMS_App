package com.hms.repository;


import com.hms.entity.AppUser_hms;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.Optional;

public interface AppUserHmsRepository extends JpaRepository<AppUser_hms, Long> {

    //AppUser_hms createPost(AppUser_hms user) ;

    Optional<AppUser_hms> findByUsername(String username);
    Optional<AppUser_hms> findByEmail(String email);
}