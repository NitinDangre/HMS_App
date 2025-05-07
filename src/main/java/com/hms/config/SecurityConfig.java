package com.hms.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;
    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        //h(cd)2
        http.csrf().disable().cors().disable();

        //haap

        //JWT Token Filter
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
        //permit all request
        http.authorizeHttpRequests().anyRequest().permitAll();
        //now want to permit spacific request or role base user
//        http.authorizeHttpRequests()
//                .requestMatchers("api/v1/users/login","api/v1/users/signUp","api/v1/users/signUp-property-owner")
//                .permitAll()
//                .requestMatchers("api/v1/country/addCountry")
//                .hasRole("OWNER")
//                .anyRequest().authenticated();

        return http.build();
    }

}
