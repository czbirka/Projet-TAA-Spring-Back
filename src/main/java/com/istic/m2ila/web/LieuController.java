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

import com.istic.m2ila.model.Lieu;
import com.istic.m2ila.service.LieuDAO;

@RestController
@RequestMapping("/lieu")
public class LieuController {

	@Autowired
	private LieuDAO lieuDao;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Lieu>> listAllLieux() {
		List<Lieu> lieux = lieuDao.findAll();
		if (lieux.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Lieu>>(lieux, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createLieu(@RequestBody Lieu lieu) {
		if (lieuDao.existsById(lieu.getId())) {
			return new ResponseEntity("Unable to create. A Lieu with name " + lieu.getNom() + " already exist.",
					HttpStatus.CONFLICT);
		}
		lieuDao.save(lieu);
		return new ResponseEntity<Lieu>(lieu, HttpStatus.CREATED);
	}

	// Path : /region/:id

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getLieu(@PathVariable("id") long id) {
		Lieu lieu = lieuDao.findById(id);
		if (lieu == null) {
			return new ResponseEntity("Lieu with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Lieu>(lieu, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLieu(@PathVariable("id") long id, @RequestBody Lieu lieu) {

		Lieu currentLieu = lieuDao.findById(id);

		if (currentLieu == null) {
			return new ResponseEntity("Unable to upate. Lieu with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}

		currentLieu.setNom(lieu.getNom());
		currentLieu.setLatitude(lieu.getLatitude());
		currentLieu.setLongitude(lieu.getLongitude());
		currentLieu.setDepartement(lieu.getDepartement());

		lieuDao.setLieuInfoById(id, lieu.getNom(), lieu.getLatitude(), lieu.getLongitude(), lieu.getDepartement());
		return new ResponseEntity<Lieu>(currentLieu, HttpStatus.OK);

	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLieu(@PathVariable("id") long id) {

		Lieu lieu = lieuDao.findById(id);
		if (lieu == null) {
			return new ResponseEntity("Unable to delete. Lieu with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		lieuDao.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	// Path : lieu/as/delete

	@RequestMapping(value = "as/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Lieu> deleteAllLieux() {
		lieuDao.deleteAllLieux();
		return new ResponseEntity<Lieu>(HttpStatus.NO_CONTENT);
	}

}
