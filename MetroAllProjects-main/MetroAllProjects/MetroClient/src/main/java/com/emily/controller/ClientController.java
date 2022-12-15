package com.emily.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.emily.entity.Customer;
import com.emily.service.ClientService;
@Controller
public class ClientController {
	@Autowired
	private ClientService service;
	
	
	// User inputs ID to login
	@RequestMapping("/")
	public ModelAndView getUserIdPage() {
		return new ModelAndView("InputCustomerId");
	}
	
	// Customer Account page
	@RequestMapping("/account")
	public ModelAndView accountController(@RequestParam("customerId") int id, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		Customer customer = service.loginCheck(id);
		
		if (customer != null) {
			session.setAttribute("customer", customer);
			modelAndView.setViewName("AccountPage");
		} else {
			modelAndView.addObject("message", "No account found with that Id, Please try again");
			modelAndView.setViewName("InputUserId");
		}
		return modelAndView;
	}
	
	// Create a new Customer
	@RequestMapping("/addNewCustomerPage")
	public ModelAndView addPageController() {
		
		return new ModelAndView("InputNewCustomer", "customer", new Customer());
	}
	
	
	@RequestMapping("/addNewCustomer")
	public ModelAndView addNewCustomerController(@ModelAttribute("customer") Customer newCustomer, @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		ModelAndView modelAndView = new ModelAndView();
		
		newCustomer.setCustomerDateOfBirth(date);
		
		Customer test = service.addNewCustomer(newCustomer);
		
		if (test != null) {
			modelAndView.setViewName("CustomerBalance");
		} else {
			modelAndView.setViewName("InputNewCustomer");
		}
		
		return modelAndView;
		
	}
}
