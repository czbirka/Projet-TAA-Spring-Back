package com.istic.m2ila.security;


import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.istic.m2ila.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javassist.NotFoundException;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    protected AccountService serv;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody AccountCredentials userparam) throws ServletException {

        String jwtToken = "";


        if (!serv.validate(userparam)) {
            throw new ServletException("Please fill in username and password");
        }

        try {
            serv.connect(userparam);
        }catch (NotFoundException ex){
            throw new ServletException(ex.getMessage());
        }


        jwtToken = Jwts.builder().setSubject(userparam.getUsername()).claim("roles", "user").setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "NotreCleSuperSecrete").compact();

        return jwtToken;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody AccountCredentials userparam) throws ServletException {

        if (!serv.validate(userparam)) {
            throw new ServletException("Please fill in username and password");
        }

        try {
            return serv.register(userparam);
        }catch (Exception ex){
            throw new ServletException(ex.getMessage());
        }



    }
}