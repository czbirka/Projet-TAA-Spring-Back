package com.istic.m2ila.scheduled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
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
    	
    	String donneesMeteo = "";
    	
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
    		if (lieuxUser.size()>0) {
    		double lat = lieuxUser.get(0).getLatitude();
    		double lon = lieuxUser.get(0).getLongitude();
    		try {
    			donneesMeteo=sendGET();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
    
    private  String sendGET() throws IOException {
    	 String GET_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=35&lon=139&APPID=79ab92ee7aba089ef7c6dbb6c96c9a54&units=metric";
    	 String USER_AGENT = "Mozilla/5.0";
    	 
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		
		System.out.println("GET Response Code :: " + responseCode);
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			//StringBuffer response = new StringBuffer();
			String response ="";

			while ((inputLine = in.readLine()) != null) {
				//response.append(inputLine);
				response += inputLine;
			}
			in.close();

			System.out.println(response);
			
			return response;
			
			//JSONObject objet = new JSONObject();
			
			
		} else {
			System.out.println("GET request not worked");
			return null;
		}

	}
    

}
