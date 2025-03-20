package com.hms.services;


import com.hms.payload.LoginDto;

import org.springframework.stereotype.Service;


public interface UserServices {
    String verifyLogin(LoginDto dto);

}
