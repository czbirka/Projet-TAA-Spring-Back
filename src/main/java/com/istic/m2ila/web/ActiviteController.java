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

import com.istic.m2ila.model.Activite;
import com.istic.m2ila.service.ActiviteDAO;

@RestController
@RequestMapping("/activite")
public class ActiviteController {

	@Autowired
	private ActiviteDAO activiteDao;

	// Path : /activite
	@RequestMapping( method = RequestMethod.GET)
	public ResponseEntity<List<Activite>> listAllActivites() {
		List<Activite> activites = activiteDao.findAll();
		if (activites.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Activite>>(activites, HttpStatus.OK);
	}

	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<?> createActivite(@RequestBody Activite activite) {
		if (activiteDao.existsById(activite.getId())) {
			return new ResponseEntity("Unable to create. A Activite with name " + activite.getNom() + " already exist.",
					HttpStatus.CONFLICT);
		}
		activiteDao.save(activite);
		return new ResponseEntity<Activite>(activite, HttpStatus.CREATED);
	}
	
	// Path : /activite/:id

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getActivite(@PathVariable("id") long id) {
		Activite activite = activiteDao.findById(id);
		if (activite == null) {
			return new ResponseEntity("Activite with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Activite>(activite, HttpStatus.OK);
	}


	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateActivite(@PathVariable("id") long id, @RequestBody Activite activite) {

		Activite currentActivite = activiteDao.findById(id);

		if (currentActivite == null) {
			return new ResponseEntity("Unable to upate. Activite with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		currentActivite.setNom(activite.getNom());
		currentActivite.setCondition(activite.getCondition());

		activiteDao.setActiviteInfoById(id, activite.getNom(), activite.getCondition());
		return new ResponseEntity<Activite>(currentActivite, HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteActivite(@PathVariable("id") long id) {

		Activite activite = activiteDao.findById(id);
		if (activite == null) {
			return new ResponseEntity("Unable to delete. Activite with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		activiteDao.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	// Path : /activite/as/delete

	@RequestMapping(value = "as/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Activite> deleteAllActivites() {
		activiteDao.deleteAllActivites();
		return new ResponseEntity<Activite>(HttpStatus.NO_CONTENT);
	}

}
