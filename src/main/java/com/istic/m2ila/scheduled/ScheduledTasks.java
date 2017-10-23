package com.istic.m2ila.scheduled;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.istic.m2ila.model.Activite;
import com.istic.m2ila.model.Lieu;
import com.istic.m2ila.model.User;
import com.istic.m2ila.service.ActiviteDAO;
import com.istic.m2ila.service.LieuDAO;
import com.istic.m2ila.service.UserDAO;

@Component
public class ScheduledTasks {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private ActiviteDAO activiteDao;
	@Autowired
	private LieuDAO lieuDao;
	
    @Scheduled(cron = "0 0 23 * * TUE")
    public void reportCurrentTime() {
    	
    }
    
    @Scheduled(fixedRate = 5000)
    public void test() {
    	List<User> users = userDao.findAll();
    	List<Activite> activites = activiteDao.findAll();
    	
    	System.out.println("USERS : " + users.size());
    	System.out.println("ACTIVITES : " + activites.size());
    	
    	for(int i=0; i<users.size(); i++) {
    		
    		System.out.println("user : "+ users.get(i).getId()+ " - " + users.get(i).getNom() + " - " + users.get(i).getPrenom());
    	
    		List<Activite> activitesUser = new ArrayList<Activite>();
    		List<Lieu> lieuxUser = new ArrayList<Lieu>();

    		for(int j=0; j<activites.size(); j++) {
    			if(activites.get(j).getUser() != null) {
    				if((activites.get(j).getUser().getId() == users.get(i).getId()) 
    						&& (!activitesUser.contains(activites.get(j)))) {
    					activitesUser.add(activites.get(j));
    				}
    			}
    		}
    		
    		System.out.println("activitesUser : " + activitesUser.size());
    		
    		for(int j=0; j<activitesUser.size(); j++) {
    			
    			System.out.println(activitesUser.get(j).getNom());
    			
    			List<Lieu> lieuxActivite = activiteDao.findLieuxById(activites.get(j).getId());
    			for (int k=0; k<lieuxActivite.size(); k++) {
    				if (!lieuxUser.contains(lieuxActivite.get(k))) {
    					lieuxUser.add(lieuxActivite.get(k));
    				}
    			}
    		}
    		
    		System.out.println("lieuxUser : " + lieuxUser.size());
    		for (int j=0; j<lieuxUser.size(); j++) {
    			System.out.println(lieuxUser.get(j).getId()+" - "+lieuxUser.get(j).getNom());
    		}
    	}
    	
    	
    	
//    	users.forEach((user) -> {
//    		List<Activite> activitesUser = new ArrayList<Activite>();
//        	List<Lieu> lieuxUser = new ArrayList<Lieu>();
//        	
//    		System.out.println("User : " + user.getNom() + " - " + user.getPrenom());
//    		
//    		activites.forEach((activite) -> {
//    			User user2 = activite.getUser();
//    			if ((user2 !=null) && (user2.getId() == user.getId()) && (!activitesUser.contains(activite))) {
//    				activitesUser.add(activite);
//    				System.out.println("Activite : " + activite.getNom());
//    			}
//    		});
//    		
    		//System.out.println("aaaaaaaaa " activitesUser.);
//    		activitesUser.forEach((activiteUser) -> {
//    			List<Lieu> listeLieux = activiteUser.getLieux();
//    			System.out.println(activiteUser.getLieux());
  //  			System.out.println("nb lieux :" + listeLieux.size());
 //   			listeLieux.forEach((lieu) -> {
//    				if (!lieuxUser.contains(lieu)) {
//    					lieuxUser.add(lieu);
//    					System.out.println("Lieu : " + lieu.getNom());
//    				}
 //   			});
    			
  //  		});
    		
   // 	});
    }
	
}
