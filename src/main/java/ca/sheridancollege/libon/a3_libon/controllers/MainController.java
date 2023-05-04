/*
 * Name: Bonita Li
 * File: MainController.java
 * Other Files in this Project:
 * 		index.html
 * 		index.html
 * 		add.html
 * 		schema.sql
 * 		data.sql
 * 		stylesheet.css
 * 		Event.java
 * 		Transaction.java
 * 		DatabaseAccess.java
 * 		DatabaseConfig.java
 */

/**
 * This is the Main Controller class that handles mapping for HTML, handling
 * sessions, and creating models for Event and Transaction objects.
 */

package ca.sheridancollege.libon.a3_libon.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.libon.a3_libon.beans.Event;
import ca.sheridancollege.libon.a3_libon.beans.Transaction;
import ca.sheridancollege.libon.a3_libon.database.DatabaseAccess;

@Controller
public class MainController {
	
	@Autowired
	private DatabaseAccess da;
	
	/**
	 * Sends web page to index.html
	 * 
	 * @param model Model for events
	 * @param session Model for sessions 
	 * @return String of index.html
	 */
	@GetMapping("/")
	public String indexPage(Model model, HttpSession session) {

		if (session.getAttribute("events") == null) {
			session.setAttribute("events", da.getEvents());
		}

		model.addAttribute("events", new Event());
		
		return "index.html";
	}

	/**
	 * 
	 * @param model Basic model for adding attributes
	 * @param session Model for sessions
	 * @return String of index.html
	 */
	@GetMapping("/events")
	public String eventsPage(Model model, HttpSession session) {
		if (session.getAttribute("events") == null) {
			session.setAttribute("events", da.getEvents());
		}

		model.addAttribute("events", new Event());
		
		return "index.html";
	}
	
	/**
	 * 
	 * @param model Basic model for adding attributes
	 * @param transaction Transaction model
	 * @return String of /txns/add.html
	 */
	@GetMapping("/add")
	public String transactionPage(Model model, @ModelAttribute Transaction transaction) {
		
		model.addAttribute("transaction", new Transaction());
		
		List<Event> events = da.getEvents();
		model.addAttribute("events", events);
		
		return "/txns/add.html";
	}
	
	/**
	 * 
	 * @param model Basic model for adding attributes
	 * @param transaction Model for Transaction
	 * @return String of redirect:/list
	 */
	@PostMapping("/addnew")
	public String addNewTransc(Model model, @ModelAttribute Transaction transaction) {
		
		da.addTransaction(transaction);

		return "redirect:/list";
	} 
	
	/**
	 * 
	 * @param model Basic model for adding attributes
	 * @return String of /txns/index.html
	 */
	@GetMapping("/list")
	public String transactionsList(Model model) {
		
		model.addAttribute("transactions", da.getTransactions());
		model.addAttribute("events", da.getEventMap());
		model.addAttribute("rows", da.getTransactionsCount());
		
		return "/txns/index.html";
	}
}
