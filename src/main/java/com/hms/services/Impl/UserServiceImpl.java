package com.hms.services.Impl;

import com.hms.entity.AppUser_hms;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserHmsRepository;
import com.hms.services.JWTServices;
import com.hms.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl  implements UserServices {
    //private ModelMapper mapper;

    private AppUserHmsRepository userRepo;
    private JWTServices jwtService;

    public UserServiceImpl(AppUserHmsRepository userRepo,JWTServices jwtService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
      //  this.mapper=mapper;
    }

    public String verifyLogin(LoginDto dto) {
        Optional<AppUser_hms> opUser = userRepo.findByUsername(dto.getUsername());
        if (opUser.isPresent()) {
            AppUser_hms appUser = opUser.get();
            if( BCrypt.checkpw(dto.getPassword(), appUser.getPassword())){
                //Generate Token
               String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }
        } else {
            return null;
        }
        return null;
    }

//
//
//    public AppUser_dto createPost(AppUser_dto userDto) {
//        // convert DTO to entity
//        AppUser_hms user = mapToEntity(userDto);
//        AppUser_hms newUser = userRepo.save(user);
//        // convert entity to DTO
//        AppUser_dto postResponse = mapToDTO(newUser);
//        return null;
//    }
//
//
//    public Optional<AppUser_dto> findByUserName(String username) {
//
//        Optional<AppUser_hms> user=userRepo.findByUserName(username);
//
//        AppUser_dto userDto = mapToDTO(user);
//        return userDto;
//    }
//
//    @Override
//    public Optional<AppUser_dto> findByEmail(String email) {
//        return Optional.empty();
//    }
//
//
//    // convert Entity into DTO
//    private AppUser_dto mapToDTO(AppUser_hms User){
//        AppUser_dto userDto = mapper.map(User, AppUser_dto.class);
////            PostDto postDto = new PostDto();
////            postDto.setId(post.getId());
////            postDto.setTitle(post.getTitle());
////            postDto.setDescription(post.getDescription());
////            postDto.setContent(post.getContent());
//        return userDto;
//    }
//    // convert DTO to entity
//    private AppUser_hms mapToEntity(AppUser_dto UserDto){
//        AppUser_hms user  = mapper.map(UserDto, AppUser_hms.class);
////            Post post = new Post();
////            post.setId(postDto.getId());
////            post.setTitle(postDto.getTitle());
////            post.setDescription(postDto.getDescription());
////            post.setContent(postDto.getContent());
//        return user;
//    }
}
