package com.example;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.domin.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DemoApplicationTests {
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void buildMockMVC() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void homePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/readingList")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("readingList"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("books"))
				.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.is(Matchers.empty())));
	}

	@Test
	public void bookPost() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/readingList").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "BOOK TITLE").param("author", "BOOK AUTHOR").param("isbn", "1234567890")
				.param("description", "DESCRIPTION")).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.header().string("Location", "/readingList"));
		Book book = new Book();
		book.setId(1L);
		book.setAuthor("BOOK AUTHOR");
		book.setIsbn("1234567890");
		book.setTitle("BOOK TITLE");
		book.setDescription("DESCRIPTION");
		book.setReader("readingList");
		mockMvc.perform(MockMvcRequestBuilders.get("/readingList")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("readingList"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("books"))
				.andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.model().attribute("books",
						Matchers.contains(Matchers.samePropertyValuesAs(book))));
	}

	@Test
	public void contextLoads() {
	}

}
