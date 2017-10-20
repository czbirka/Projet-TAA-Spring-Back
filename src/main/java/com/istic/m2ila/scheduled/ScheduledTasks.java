package com.istic.m2ila.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	public void planificationWE() {
		
	}
	
    @Scheduled(cron = "0 0 23 * * TUE")
    public void reportCurrentTime() {
    	
    }
    
    @Scheduled(fixedRate = 1000)
    public void test() {
    	System.out.println("coucou amine");
    }
	
}
