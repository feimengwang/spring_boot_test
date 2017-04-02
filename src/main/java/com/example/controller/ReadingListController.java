package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.AmazonConfiguration;
import com.example.domin.Book;
import com.example.repository.ReadingListRepository;

@Controller
@RequestMapping("/")
public class ReadingListController {

	@Autowired
	private AmazonConfiguration zAmazonConfiguration;
	

	@Autowired
	ReadingListRepository readingListRepository;

	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String getBookList(@PathVariable("reader") String reader, Model model) {
		List<Book> books = readingListRepository.findByReader(reader);
		if (books != null) {
			model.addAttribute("books", books);
		}
		model.addAttribute("associateId",zAmazonConfiguration.getAssociateId());
		return "readingList";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addBook(@PathVariable("reader") String reader, Book book) {
		book.setReader(reader);
		readingListRepository.save(book);
		
		return "redirect:/{reader}";
	}
}
