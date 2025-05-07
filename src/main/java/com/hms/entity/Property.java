package com.hms.entity;

import jakarta.persistence.*;

@Entity
@Table(name="HMS_PROPERTY")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="no_of_guests",nullable = false)
    private Long no_of_guests;

    @Column(name="no_of_bedrooms",nullable = false)
    private Long no_of_bedrooms;

    @Column(name="no_of_bathrooms",nullable = false)
    private Long no_of_bathrooms;

    @Column(name="no_of_beds",nullable = false)
    private Long no_of_beds;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;
}
