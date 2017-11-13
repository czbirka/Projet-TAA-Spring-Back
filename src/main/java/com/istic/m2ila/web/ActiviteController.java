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

import com.istic.m2ila.model.Activite;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.ActiviteDAO;

@RestController
@RequestMapping("/activite")
public class ActiviteController {

	@Autowired
	private ActiviteDAO activiteDao;

	@ResponseBody
	@RequestMapping(
			value = "", 
			method = RequestMethod.GET, 
			produces="application/json")
	public ResponseEntity<List<Activite>> listAllActivites() {
		List<Activite> activites = activiteDao.findAll();
		if (activites.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Activite>>(activites, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(
			value = "/user/{id}", 
			method = RequestMethod.GET, 
			produces="application/json")
	public ResponseEntity<List<Activite>> listActivitesByUser(@PathVariable("id") long id) {
		List<Activite> activites = activiteDao.findActivitiesByUserId(id);
		if (activites.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Activite>>(activites, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(
			value = "/{id}", 
			method = RequestMethod.GET, 
			produces="application/json")
	public ResponseEntity<?> getActivite(@PathVariable("id") long id) {
		Activite activite = activiteDao.findById(id);
		if (activite == null) {
			return new ResponseEntity("Activite with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Activite>(activite, HttpStatus.OK);
	}
	
	@ResponseBody
	@RequestMapping(
    		value = "", 
    		method = RequestMethod.POST, 
    		produces="application/json", 
    		consumes="application/json")
	public ResponseEntity<?> createActivite(@RequestBody Activite activite) {
		//List<Activite> activites = activiteDao.findByName(activite.getNom());
		//if (activites.size()==0) {
			activiteDao.save(activite);
			return new ResponseEntity<Activite>(activite, HttpStatus.CREATED);
//		}
//		else {
//			for (int i=0; i<activites.size(); i++) {
//				if (activite.getUser() == null) {
//					if (activites.get(i).getUser() == null) {
//						return new ResponseEntity("Unable to create. A Activite with name " 
//								+ activite.getNom() + " already exist.", HttpStatus.CONFLICT);
//					}
//				}
//				else {
//					if ((activites.get(i).getUser() != null) && 
//							(activites.get(i).getUser().getId() == activite.getUser().getId())) {
//						return new ResponseEntity("Unable to create. A Activite with name " 
//								+ activite.getNom() + " already exist.", HttpStatus.CONFLICT);
//					}
//				}
//			}
//		}
//		activiteDao.save(activite);
//		return new ResponseEntity<Activite>(activite, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.PUT, 
    		produces="application/json", 
    		consumes="application/json")
	public ResponseEntity<?> updateActivite(@PathVariable("id") long id, @RequestBody Activite activite) {
		Activite currentActivite = activiteDao.findById(id);
		if (currentActivite == null) {
			return new ResponseEntity("Unable to upate. Activite with id " + id 
					+ " not found.", HttpStatus.NOT_FOUND);
		}
		if (activite.getNom() != null) {
			currentActivite.setNom(activite.getNom());
		}
		if (activite.getCondition().getTempMin() != -999) {
			currentActivite.getCondition().setTempMin(activite.getCondition().getTempMin());
		}
		if (activite.getCondition().getTempInf() != -999) {
			currentActivite.getCondition().setTempInf(activite.getCondition().getTempInf());
		}
		if (activite.getCondition().getTempSup() != 999) {
			currentActivite.getCondition().setTempSup(activite.getCondition().getTempSup());
		}
		if (activite.getCondition().getTempMax() != 999) {
			currentActivite.getCondition().setTempMax(activite.getCondition().getTempMax());
		}
		if (activite.getCondition().getVentMin() != -999) {
			currentActivite.getCondition().setVentMin(activite.getCondition().getVentMin());
		}
		if (activite.getCondition().getVentInf() != -999) {
			currentActivite.getCondition().setVentInf(activite.getCondition().getVentInf());
		}
		if (activite.getCondition().getVentSup() != 999) {
			currentActivite.getCondition().setVentSup(activite.getCondition().getVentSup());
		}
		if (activite.getCondition().getVentMax() != 999) {
			currentActivite.getCondition().setVentMax(activite.getCondition().getVentMax());
		}
		if (activite.getUser() != null) {currentActivite.setUser(activite.getUser());}

		activiteDao.save(currentActivite);
		return new ResponseEntity<Activite>(currentActivite, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.DELETE, 
    		consumes="application/json")
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
