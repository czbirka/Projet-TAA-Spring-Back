package com.istic.m2ila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.istic.m2ila.model.User;


//@RestController
//@EnableAutoConfiguration
@SpringBootApplication
public class Application {
    
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		

		
		/*
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ApplicationTableModel tableModel = (ApplicationTableModel) view.getApplicationTableView().getModel();
                tableModel.update();
            }
        }, 0, 5000);
		*/
		
		
	}
	
}
