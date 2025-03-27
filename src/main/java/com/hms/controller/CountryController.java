package com.hms.controller;

import com.hms.entity.AppUser_hms;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/country")
public class CountryController {

//    @PostMapping("/country")
//    public String CreateContry(@RequestBody AppUser_hms user){
//        System.out.println("Contry");
//        return "India Access";
//    }

    @PostMapping("/addCountry")
    public String CreateAddContry(@RequestBody AppUser_hms user){
        System.out.println("AddContry");
        return "India Add   Access";
    }
}
