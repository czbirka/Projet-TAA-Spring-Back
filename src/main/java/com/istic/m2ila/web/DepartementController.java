package com.istic.m2ila.web;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.istic.m2ila.model.Departement;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.DepartementDAO;

@RestController
@RequestMapping("/departement")
public class DepartementController {

	@Autowired
	private DepartementDAO departementDao;
	
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Departement>> listAllDepartements() {
        List<Departement> departements = departementDao.findAll();
        if (departements.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Departement>>(departements, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<?> getDepartement(@PathVariable("id") long id) {
    	Departement departement = departementDao.findById(id);
        if (departement == null) {
            return new ResponseEntity("Departement with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Departement>(departement, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createDepartement(@RequestBody Departement departement) {
        if (departementDao.existsById(departement.getId())) {
            return new ResponseEntity("Unable to create. A User with name " + 
            		departement.getNom() + " already exist.",HttpStatus.CONFLICT);
        }
        departementDao.save(departement);
        return new ResponseEntity<Departement>(departement, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateDepartement(@PathVariable("id") long id, @RequestBody Departement departement) {
 
    	Departement currentDepartement = departementDao.findById(id);
 
        if (currentDepartement == null) {
            return new ResponseEntity("Unable to upate. Departement with id " + id + " not found.", 
            		HttpStatus.NOT_FOUND);
        }
 
        currentDepartement.setNom(departement.getNom());
        currentDepartement.setRegion(departement.getRegion());
              
        departementDao.setDepartementInfoById(id, departement.getNom(), departement.getRegion());
        return new ResponseEntity<Departement>(currentDepartement, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDepartement(@PathVariable("id") long id) {
 
    	Departement departement = departementDao.findById(id);
        if (departement == null) {
            return new ResponseEntity("Unable to delete. Departement with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        departementDao.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Departement> deleteAllDepartements() {
    	departementDao.deleteAllDepartements();
        return new ResponseEntity<Departement>(HttpStatus.NO_CONTENT);
    }
    
}
