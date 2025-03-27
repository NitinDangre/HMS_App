package com.hms.config;

import com.hms.entity.AppUser_hms;
import com.hms.repository.AppUserHmsRepository;
import com.hms.services.JWTServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
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
                System.out.println("UserRole:::"+appUser.getRole());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(appUser, null,
                                Collections.singleton(new SimpleGrantedAuthority(appUser.getRole())));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Authenticated User Roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    System.out.println("Authenticated User: " + authentication.getName());
                    System.out.println("Authorities: " + authentication.getAuthorities());
                }

            }
        }else {
            // Handle invalid token case
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return;
        }
        filterChain.doFilter(request,response);

    }
}

//{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiRGluZXNoIiwiaXNzIjoiTml0aW5fRGFuZ3JlIiwiZXhwIjoxNzQyNTM4ODMxfQ.TT3f5784K09r3gD0W5OnSn3XDmJ9iX2GREY8C6aimeQ","type":"JWT"}