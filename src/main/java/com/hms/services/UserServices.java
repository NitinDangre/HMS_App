package com.hms.services;


import com.hms.payload.LoginDto;

import org.springframework.stereotype.Service;


public interface UserServices {
    boolean verifyLogin(LoginDto dto);

}
