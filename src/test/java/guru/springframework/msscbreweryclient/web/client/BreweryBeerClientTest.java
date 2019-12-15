package guru.springframework.msscbreweryclient.web.client;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class BreweryBeerClientTest {

	@Autowired
	BreweryBeerClient client;

	@Test
	void testGetBeer() {
		UUID uuid = UUID.randomUUID();

		log.debug("Trying to get beer identified by {}", uuid);
		BeerDto beer = client.getBeerById(UUID.randomUUID());

		assertNotNull(beer);
		log.debug("Successfully got beer {}", beer);
	}

	@Test
	void testSaveBeer() {
		BeerDto beer = BeerDto.builder().beerName("Beer Creation Test").build();

		URI uri = client.saveBeer(beer);

		assertNotNull(uri);
	}

	@Test
	void testEditBeer() {
		UUID uuid = UUID.randomUUID();
		BeerDto beer = BeerDto.builder().id(uuid).beerName("Beer Edition Test").build();

		log.debug("Trying to update beer {}", beer);
		client.updateBeer(uuid, beer);

		log.debug("Successfully updated beer {}", beer);
	}
	
	@Test
	void testDeleteBeer() {
		UUID uuid = UUID.randomUUID();

		log.debug("Trying to delete beer identified by {}", uuid);
		client.deleteBeer(uuid);

		log.debug("Successfully deleted beer {}", uuid);
	}

}
