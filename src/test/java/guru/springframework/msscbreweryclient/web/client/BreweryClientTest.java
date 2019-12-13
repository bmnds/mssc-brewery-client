package guru.springframework.msscbreweryclient.web.client;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.BeerDto;

@SpringBootTest
class BreweryClientTest {
	
	@Autowired BreweryClient client;

	@Test
	void test() {
		 BeerDto dto = client.getBeerById(UUID.randomUUID());
		 assertNotNull(dto);
	}

}
