package com.istic.m2ila.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.istic.m2ila.model.User;
import com.istic.m2ila.service.UserDAO;

import javassist.NotFoundException;

@Service
public class AccountService {

    @Autowired
    public UserDAO userServ;

     public boolean validate(AccountCredentials authObj){
         return authObj.getUsername()!=null && authObj.getPassword()!=null;
     }

     private boolean exist(String login){
         return userServ.findByLogin(login).size()>0;
     }

     public User register(AccountCredentials authObj) throws Exception {
         if(exist(authObj.getUsername())){
             throw new Exception("Ce login est déjà utilisé, veuillez en choisir un autre");
         }
         User user = new com.istic.m2ila.model.User();
         user.setLogin(authObj.getUsername());
         user.setMotDePasse(authObj.getPassword()); 
         return userServ.save(user);
     }

     public void connect (AccountCredentials authObj) throws NotFoundException {
         List<User> users = userServ.findByLoginAndMotDePasse(authObj.getUsername(), authObj.getPassword());

         if(users.size()==0){
             throw new NotFoundException("L'identifiant utilisateur ou le mot de passe sont incorrect.");
         }

     }
}
