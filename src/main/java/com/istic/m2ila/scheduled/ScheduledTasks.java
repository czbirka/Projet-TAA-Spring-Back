package com.istic.m2ila.scheduled;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.istic.m2ila.model.User;
import com.istic.m2ila.service.UserDAO;

@Component
public class ScheduledTasks {

	@Autowired
	private UserDAO userDao;
	
    @Scheduled(cron = "0 0 23 * * TUE")
    public void reportCurrentTime() {
    	
    }
    
    @Scheduled(fixedRate = 5000)
    public void test() {
    	List<User> users = userDao.findAll();
    	users.forEach((user) -> {
    		//System.out.println(user.getNom() + " - " + user.getPrenom());
    		
    	});
    }
	
}
