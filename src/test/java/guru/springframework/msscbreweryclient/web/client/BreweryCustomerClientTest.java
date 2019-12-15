package guru.springframework.msscbreweryclient.web.client;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.Customer;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class BreweryCustomerClientTest {

	@Autowired
	BreweryCustomerClient client;

	@Test
	void testGetCustomer() {
		UUID uuid = UUID.randomUUID();
		
		log.debug("Trying to get customer identified by {}", uuid);
		Customer customer = client.getCustomerById(uuid);
		
		assertNotNull(customer);
		log.debug("Successfully got customer {}", customer);
	}
	
	@Test
	void testSaveCustomer() {
		Customer customer = Customer.builder().name("Customer Creation Test").build();
		
		log.debug("Trying to save customer {}", customer);
		URI uri = client.saveCustomer(customer);
		
		assertNotNull(uri);
		log.debug("Successfully saved customer at {}", uri);
	}
	
	@Test
	void testEditCustomer() {
		UUID uuid = UUID.randomUUID();
		Customer customer = Customer.builder().id(uuid).name("Customer Edition Test").build();
		
		log.debug("Trying to update customer {}",  customer);
		client.updateCustomer(uuid, customer);
		
		log.debug("Successfully updated customer {}", customer);
	}
	
	@Test
	void testDeleteCustomer() {
		UUID uuid = UUID.randomUUID();
		
		log.debug("Trying to delete customer {}",  uuid);
		client.deleteCustomer(uuid);
		
		log.debug("Successfully deleted customer {}", uuid);
	}

}
