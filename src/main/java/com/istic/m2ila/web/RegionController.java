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

import com.istic.m2ila.model.Region;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.RegionDAO;

@RestController
@RequestMapping("/region")
public class RegionController {

	@Autowired
	private RegionDAO regionDao;

	// Path : /region

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
	
	@RequestMapping(
			value = "{id}", 
			method = RequestMethod.GET, 
			produces="application/json")
	public ResponseEntity<?> getRegion(@PathVariable("id") long id) {
		Region region = regionDao.findById(id);
		if (region == null) {
			return new ResponseEntity("Region with id " + id + " not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Region>(region, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createRegion(@RequestBody Region region) {
		if (regionDao.findByNom(region.getNom()).size() != 0) {
			return new ResponseEntity("Unable to create. A Region with name " + region.getNom() + " already exist.",
					HttpStatus.CONFLICT);
		}
		regionDao.save(region);
		return new ResponseEntity<Region>(region, HttpStatus.CREATED);
	}

	
	@RequestMapping(
			value = "{id}", 
			method = RequestMethod.PUT, 
    		produces="application/json", 
    		consumes="application/json")
	public ResponseEntity<?> updateRegion(@PathVariable("id") long id, @RequestBody Region region) {
		Region currentRegion = regionDao.findById(id);
		if (currentRegion == null) {
			return new ResponseEntity("Unable to upate. Region with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		if (region.getNom() != null) {currentRegion.setNom(region.getNom());}
		regionDao.save(currentRegion);
		return new ResponseEntity<Region>(currentRegion, HttpStatus.OK);
	}

	@RequestMapping(
			value = "{id}", 
			method = RequestMethod.DELETE, 
			consumes="application/json")
	public ResponseEntity<?> deleteRegion(@PathVariable("id") long id) {
		Region region = regionDao.findById(id);
		if (region == null) {
			return new ResponseEntity("Unable to delete. Region with id " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		regionDao.deleteById(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	

	
	
	
	

	// Path : /region/as/delete
	@RequestMapping(value="as/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Region> deleteAllRegions() {
		regionDao.deleteAllRegions();
		return new ResponseEntity<Region>(HttpStatus.NO_CONTENT);
	}
	

}
