package com.hms.controller;

import com.hms.entity.AppUser_hms;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.repository.AppUserHmsRepository;
import com.hms.services.Impl.UserServiceImpl;
import com.hms.services.UserServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private AppUserHmsRepository userRepository;
    private UserServices userService;
    public void userController(AppUserHmsRepository userRepository){
        this.userRepository=userRepository;
    }
    public UserController(UserServices userService) {
        this.userService = userService;
    }
    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(@RequestBody AppUser_hms user){
        Optional<AppUser_hms> opUser=userRepository.findByUsername(user.getUsername());
        if(opUser.isPresent()){
           return new ResponseEntity<>("username Already Present ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser_hms> opEmail=userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email Already Present ", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String encryptedPass=BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(5));
        user.setPassword(encryptedPass);
        AppUser_hms saveUser=userRepository.save(user);
        return new ResponseEntity<>(saveUser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto Dto){
       String token=userService.verifyLogin(Dto);
       if(token!=null){
           TokenDto tokenDto=new TokenDto();
           tokenDto.setToken(token);
           tokenDto.setType("JWT");
          return new ResponseEntity<>(tokenDto,HttpStatus.OK);
       }else{
           return new ResponseEntity<>("Invalid Login Credential",HttpStatus.FORBIDDEN);
       }
       }
    }

