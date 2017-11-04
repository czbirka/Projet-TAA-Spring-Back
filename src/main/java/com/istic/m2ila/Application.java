package com.istic.m2ila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.istic.m2ila.scheduled.MailingService;
import com.istic.m2ila.security.JwtFilter;

@SpringBootApplication
@EnableScheduling
public class Application extends WebSecurityConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");

		MailingService mm = new MailingService();
		MailSender ms = (MailSender) context.getBean("mailSender");
		mm.setMailSender(ms);
		mm.sendMail("appweekend@gmail.com", "tolo1993@yopmail.com", "Testing Appweekend",
				"Testing only \n\n Hello Appweekend Test");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Autoriser uniquement ces routes sans être identifié
		web.ignoring().antMatchers("/account/login", "/account/register");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
