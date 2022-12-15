package com.emily.demo;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.emily.entity.Customer;
import com.emily.persistance.TripDao;
import com.emily.service.ClientServiceImpl;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class MetroClientApplicationTests {

	@Test
	void contextLoads() {
	}

	

	@InjectMocks
	private ClientServiceImpl service;
	@Mock
	private TripDao dao;
	@Mock
	RestTemplate restTemplate;
	private AutoCloseable autoCloseable;

	@BeforeEach
	void setUp() throws Exception {
		autoCloseable = MockitoAnnotations.openMocks(this);

	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	
	@Order(1)
	@Test
	public void loginCheckTest() {
		LocalDate dob = LocalDate.of(1997, 8, 17);
		Customer customer=new Customer(101,"Bobby", "Moore", "bobby@gmail.com",dob,100);
		when(restTemplate.getForObject("http://localhost:8089/customers/"+101, Customer.class).thenReturn(customer);
		
		assertEquals(customer, service.loginCheck(101));
	}
	
}
