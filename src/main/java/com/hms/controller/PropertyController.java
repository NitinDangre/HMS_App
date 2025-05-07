package com.hms.controller;


import com.hms.entity.Property;
import com.hms.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }



    @GetMapping("/search-hotels")
    public List<Property> searchHotels(@RequestParam String city) {
        System.out.println("Param User: " + city);
        System.out.println("Param User: " + propertyRepository.searchHotels(city));
         List<Property> proper=propertyRepository.searchHotels(city);
         return proper;
    }

}
