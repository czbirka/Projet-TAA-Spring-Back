package com.istic.m2ila;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.istic.m2ila.model.User;


@SpringBootApplication
@EnableScheduling
public class Application {
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		
	}
	
	

	
	
	
}
