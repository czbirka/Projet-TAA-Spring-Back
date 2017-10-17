package com.istic.m2ila.web;

import java.util.List;

//import javax.ws.rs.Consumes;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.istic.m2ila.model.Region;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.RegionDAO;

@RestController
@RequestMapping("/region")
public class RegionController {

	@Autowired
	private RegionDAO regionDao;
	
	@ResponseBody
    @RequestMapping(
    		value = "", 
    		method = RequestMethod.GET, 
    		produces="application/json")
    public ResponseEntity<List<Region>> listAllRegions() {
        List<Region> regions = regionDao.findAll();
        if (regions.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Region>>(regions, HttpStatus.OK);
    }
	
	@ResponseBody
    @RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.GET, 
    		produces="application/json")
    public ResponseEntity<?> getRegion(@PathVariable("id") long id) {
    	Region region = regionDao.findById(id);
        if (region == null) {
            return new ResponseEntity("Region with id " + id + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Region>(region, HttpStatus.OK);
    }
    
	@ResponseBody
    @RequestMapping(
    		value = "", 
    		method = RequestMethod.POST, 
    	    produces="application/json", 
    		consumes = "application/json")
    public ResponseEntity<?> createRegion(@RequestBody Region region) {
        if (regionDao.findByNom(region.getNom()).size() != 0) {
            return new ResponseEntity("Unable to create. A Region with name " + 
            		region.getNom() + " already exist.",HttpStatus.CONFLICT);
        }
        regionDao.save(region);
        return new ResponseEntity<Region>(region, HttpStatus.CREATED);
    }
	
	@ResponseBody
    @RequestMapping(
    		value = "/{id}", 
    		method = RequestMethod.PUT, 
    	    produces="application/json", 
    		consumes = "application/json")
    public ResponseEntity<?> updateRegion(@PathVariable("id") long id, @RequestBody Region region) {
    	Region currentRegion = regionDao.findById(id);
        if (currentRegion == null) {
            return new ResponseEntity("Unable to upate. Region with id " + id + " not found.", 
            		HttpStatus.NOT_FOUND);
        }
        if (region.getNom() != null) {currentRegion.setNom(region.getNom());}
        regionDao.setRegionInfoById(id, region.getNom());
        return new ResponseEntity<Region>(currentRegion, HttpStatus.OK);
    }
    /*
     * public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
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
     */
	
	
	
	
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRegion(@PathVariable("id") long id) {
 
    	Region region = regionDao.findById(id);
        if (region == null) {
            return new ResponseEntity("Unable to delete. Region with id " + id + " not found.", HttpStatus.NOT_FOUND);
        }
        regionDao.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Region> deleteAllRegions() {
    	regionDao.deleteAllRegions();
        return new ResponseEntity<Region>(HttpStatus.NO_CONTENT);
    }
    
}
