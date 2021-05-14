package com.example.palindrome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@org.springframework.web.bind.annotation.RestController
public class RestController {
	
	@ResponseBody
	@GetMapping("/isPalindrome")
	String showInfo(@RequestParam("text") String text) {
		return Palindrome.isPalindrome(text).toString();		
	}
	
	
}
