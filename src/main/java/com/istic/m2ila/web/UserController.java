package com.istic.m2ila.web;

import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.UserDAO;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDAO userDao;


    @RequestMapping(value = "", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        User user = userDao.findById(id);
        if (user == null) {
            return new ResponseEntity("User with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    
    
    @RequestMapping(
    		value = "", 
    		method = RequestMethod.POST, 
    		produces="application/json", 
    		consumes="application/json")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody User user) {
    	if (userDao.findByLogin(user.getLogin()).size() != 0) {
            return new ResponseEntity("Unable to create. A User with login " + 
            user.getLogin() + " already exist.",HttpStatus.CONFLICT);
        }    	
        userDao.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }  
    
  
    
    
    @RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.PUT, 
    		produces="application/json", 
    		consumes="application/json")
    @ResponseBody
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        User currentUser = userDao.findById(id);
        if (currentUser == null) {
            return new ResponseEntity("Unable to upate. User with id " + id + " not found.", 
            		HttpStatus.NOT_FOUND);
        }
        if (user.getNom() != null) {currentUser.setNom(user.getNom());}
        if (user.getPrenom() != null) {currentUser.setPrenom(user.getPrenom());}
        if (user.getLogin() != null) {currentUser.setLogin(user.getLogin());}
        if (user.getMotDePasse() != null) {currentUser.setMotDePasse(user.getMotDePasse());}
        if (user.getEmail() != null) {currentUser.setEmail(user.getEmail());}
        userDao.save(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
    
    
    
    
    @RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.DELETE, 
    		consumes="application/json")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        User user = userDao.findById(id);
        if (user == null) {
            return new ResponseEntity("Unable to delete. U/ser with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        userDao.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
    
    
    
    
    
    @RequestMapping(
    		value = "/deleteAll", 
    		method = RequestMethod.DELETE, 
    		produces="application/json", 
    		consumes="application/json")
    @ResponseBody
    public ResponseEntity<User> deleteAllUsers() {
    	
    	List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        
        for (int i=0; i<users.size(); i++) {
        	userDao.deleteById(users.get(i).getId());
        }
   	
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
	
    
    
    


}
