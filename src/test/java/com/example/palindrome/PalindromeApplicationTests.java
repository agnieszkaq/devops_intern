package com.example.palindrome;

import org.junit.jupiter.api.Test;
import org.junit.platform.engine.SelectorResolutionResult.Status;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest 
@AutoConfigureMockMvc
class PalindromeApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void justAnExample()  {
		String text = "Aga";
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/isPalindrome")
			.param("text", text))		
			.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string("true"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}