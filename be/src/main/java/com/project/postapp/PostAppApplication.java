package com.project.postapp;

import com.project.postapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PostAppApplication.class, args);
	}



	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
//		User user = new User();    //this is a mock data
//		user.setUsername("Test User");
//		user.setPassword("Test User");
//		user.setAvatar(1);
//		userRepository.save(user);

	}
}
