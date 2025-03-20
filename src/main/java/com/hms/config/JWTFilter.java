package com.hms.config;

import com.hms.entity.AppUser_hms;
import com.hms.repository.AppUserHmsRepository;
import com.hms.services.JWTServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private AppUserHmsRepository userHmsRepository;
    private JWTServices jwtService;
    public JWTFilter(JWTServices jwtService,AppUserHmsRepository userHmsRepository) {
        this.jwtService = jwtService;
        this.userHmsRepository=userHmsRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token =request.getHeader( "Authorization");
        System.out.println("token:::"+token);
        if(token!=null && token.startsWith("Bearer ")){
            String TokenValue=token.substring(8,token.length()-1);
            System.out.println("TokenValue:::"+TokenValue);
            String Username=jwtService.getUserName(TokenValue);
            System.out.println("Username:::"+Username);
            Optional<AppUser_hms> opsUser= userHmsRepository.findByUsername(Username);
            if(opsUser.isPresent()){
                AppUser_hms appUser= opsUser.get();
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(appUser,null,null);
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);

    }
}

//{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiRGluZXNoIiwiaXNzIjoiTml0aW5fRGFuZ3JlIiwiZXhwIjoxNzQyNTM4ODMxfQ.TT3f5784K09r3gD0W5OnSn3XDmJ9iX2GREY8C6aimeQ","type":"JWT"}