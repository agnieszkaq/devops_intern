package com.example.palindrome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PalindromeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalindromeApplication.class, args);
		System.out.print("Spring Boot Application is running!");
		System.out.print("Write in browser, http://localhost:8080/isPalindrome?text=yourSpecyficWord");
	
	}

}
