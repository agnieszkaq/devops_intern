package com.example.palindrome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PalindromeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PalindromeApplication.class, args);
		System.out.println("-------------- SPRING BOOT APPLICATION IS RUNNING! --------------");
		System.out.println("Write in browser: http://localhost:8081/isPalindrome?text=yourSpecyficWord");
	
	}

}
