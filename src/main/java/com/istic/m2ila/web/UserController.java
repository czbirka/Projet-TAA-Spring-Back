package com.istic.m2ila.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.istic.m2ila.model.User;
import com.istic.m2ila.service.UserDAO;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDAO userDao;

	// Path : /user

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userDao.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) {
		if (userDao.existsById(user.getId())) {
			return new ResponseEntity("Unable to create. A User with name " + user.getNom() + " already exist.",
					HttpStatus.CONFLICT);
		}
		userDao.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	// Path : /user/:id

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		User user = userDao.findById(id);
		if (user == null) {
			return new ResponseEntity("User with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {

		User currentUser = userDao.findById(id);

		if (currentUser == null) {
			return new ResponseEntity("Unable to upate. User with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		currentUser.setNom(user.getNom());
		currentUser.setPrenom(user.getPrenom());
		currentUser.setLogin(user.getLogin());
		currentUser.setMotDePasse(user.getMotDePasse());
		currentUser.setEmail(user.getEmail());

		userDao.setUserInfoById(id, user.getNom(), user.getPrenom(), user.getLogin(), user.getMotDePasse(),
				user.getEmail());
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {

		User user = userDao.findById(id);
		if (user == null) {
			return new ResponseEntity("Unable to delete. User with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		userDao.deleteById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// Path : /user/as/delete
	@RequestMapping(value = "as/delete", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		userDao.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// @RequestMapping(method = RequestMethod.GET)
	// public List<User> get(){
	// return userDao.findAll();
	// }
	//
	// @RequestMapping(value = "{id}", method = RequestMethod.GET)
	// public User get(@PathVariable("id") long id){
	// return userDao.findById(id);
	// }
	//
	// @RequestMapping(method = RequestMethod.PUT)
	// public User create(@RequestBody User user){
	// return userDao.save(user);
	// }

	// @RequestMapping(value = "{id}", method = RequestMethod.PUT)
	// public T update(@RequestBody T obj, @PathVariable("id") int id){
	// return serv.save(obj);
	// }

	// @GetMapping("/")
	// @Transactional(readOnly = true)
	// public ModelAndView index() {
	// List<Note> notes = this.noteRepository.findAll();
	// ModelAndView modelAndView = new ModelAndView("index");
	// modelAndView.addObject("notes", notes);
	// return modelAndView;
	// }

}
