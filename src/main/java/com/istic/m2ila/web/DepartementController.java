package com.istic.m2ila.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.istic.m2ila.model.Departement;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.DepartementDAO;

@RestController
@RequestMapping("/departement")
public class DepartementController {

	@Autowired
	private DepartementDAO departementDao;

	@ResponseBody
	@RequestMapping(
			value = "", 
			method = RequestMethod.GET, 
			produces="application/json")
	public ResponseEntity<List<Departement>> listAllDepartements() {
		List<Departement> departements = departementDao.findAll();
		if (departements.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Departement>>(departements, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(
			value = "{id}", 
			method = RequestMethod.GET, 
			produces="application/json")
	public ResponseEntity<?> getDepartement(@PathVariable("id") long id) {
		Departement departement = departementDao.findById(id);
		if (departement == null) {
			return new ResponseEntity("Departement with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Departement>(departement, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(
			value = "region/{regionName}", 
			method = RequestMethod.GET, 
			produces="application/json")
	public ResponseEntity<?> getDepartementsByRegion(@PathVariable("regionName") String regionName) {
		List<Departement> departements = departementDao.findByRegion(regionName);
		if (departements == null) {
			return new ResponseEntity("Not found Departements with region " + regionName, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Departement>>(departements, HttpStatus.OK);

	}
	
	@RequestMapping(
    		value = "", 
    		method = RequestMethod.POST, 
    		produces="application/json", 
    		consumes="application/json")
	@ResponseBody
	public ResponseEntity<?> createDepartement(@RequestBody Departement departement) {
		if (departementDao.findByNom(departement.getNom()).size() != 0) {
			return new ResponseEntity("Unable to create. A User with name " + departement.getNom() +
					" already exist.", HttpStatus.CONFLICT);
		}
		departementDao.save(departement);
		return new ResponseEntity<Departement>(departement, HttpStatus.CREATED);
	}
	
	@RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.PUT, 
    		produces="application/json", 
    		consumes="application/json")
    @ResponseBody
	public ResponseEntity<?> updateDepartement(@PathVariable("id") long id, @RequestBody Departement departement) {
		Departement currentDepartement = departementDao.findById(id);
		if (currentDepartement == null) {
			return new ResponseEntity("Unable to upate. Departement with id " + id + " not found.",
					HttpStatus.NOT_FOUND);
		}
		if (departement.getNom() != null) {currentDepartement.setNom(departement.getNom());}
		if (departement.getRegion() != null) {currentDepartement.setRegion(departement.getRegion());}
		departementDao.save(currentDepartement);
		return new ResponseEntity<Departement>(currentDepartement, HttpStatus.OK);
	}
	
	@RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.DELETE, 
    		consumes="application/json")
    @ResponseBody
	public ResponseEntity<?> deleteDepartement(@PathVariable("id") long id) {
		Departement departement = departementDao.findById(id);
		if (departement == null) {
			return new ResponseEntity("Unable to delete. Departement with id " + id + " not found.",
					HttpStatus.NOT_FOUND);
		}
		departementDao.deleteById(id);
		return new ResponseEntity<Departement>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "as/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Departement> deleteAllDepartements() {
		departementDao.deleteAllDepartements();
		return new ResponseEntity<Departement>(HttpStatus.NO_CONTENT);
	}

}
