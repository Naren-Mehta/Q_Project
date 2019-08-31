package com.wm.brta.service;

import java.util.HashSet;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wm.brta.Application;
import com.wm.brta.domain.Customer;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.util.BaleUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Application.class })
@TestPropertySource(locations = "classpath:application-test.properties")
public class CustomerServiceImplTest {

	private static final Logger LOGGER = Logger
            .getLogger("CustomerServiceImplTest");
	
	@Autowired
	CustomerService customerService;

	@Test
	public void testGetAllDestination() {	
		
		String storeId="SAM4738";
		HashSet<Destination> destinationList = new HashSet<Destination>();
		HashSet<PickupDetails> picHashSet = new HashSet<PickupDetails>();
		Destination destination = new Destination();
		destination.setDestinationId(1234);
		destination.setName("yo");

		PickupDetails pickupDetails = new PickupDetails();
		picHashSet.add(pickupDetails);
//		destination.setPickupDetails(picHashSet);
		destinationList.add(destination);
		
		BaleUtils.setUpMokito();
		customerService = Mockito.mock(CustomerService.class);

		Mockito.when(
				customerService.getAllDestination(storeId))
				.thenReturn(destinationList);
		
		HashSet<Destination> destinations=customerService.getAllDestination("SAM4738");
			
	}
	
	/*@Test
	public void testGetCustomerSites() {
		
		Customer customer =new Customer();
		customer.setCustomerId(532);
		customer.setCustomerName("WAL MART");
		
		HashSet<CustomerSite> customerSites=new HashSet<CustomerSite>();
		
		BaleUtils.setUpMokito();
		customerService=Mockito.mock(CustomerService.class);
		
		
		Mockito.when(
				customerService.getCustomerSites(customer))
				.thenReturn(customerSites);
		
		HashSet<CustomerSite> destinations=customerService.getCustomerSites(customer);
		System.out.println("---customer site--"+destinations);		
	}
	*/
	
	@Test
	public void testGetCustomerSites() {
		Customer customer =new Customer();
		customer.setCustomerId(532);
		customer.setCustomerName("WAL MART");
	}
}
