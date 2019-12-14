package guru.springframework.msscbreweryclient.web.client;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.Customer;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class BreweryClientTest {

	@Autowired
	BreweryClient client;

	@Test
	void testGetBeer() {
		BeerDto dto = client.getBeerById(UUID.randomUUID());

		assertNotNull(dto);
	}
	
	@Test
	void testSaveBeer() {
		BeerDto beer = BeerDto.builder().beerName("Beer Creation Test").build();
		
		URI uri = client.saveBeer(beer);
		
		assertNotNull(uri);
	}
	
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

}
