package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "sfg.brewery.api", ignoreUnknownFields = true)
public class BreweryBeerClient {
	
	private static final String BEER_COLLECTION_PATH_V1;
	private static final String SINGLE_BEER_PATH_V1;
	
	static {
		BEER_COLLECTION_PATH_V1 = "/api/v1/beers";
		SINGLE_BEER_PATH_V1 = BEER_COLLECTION_PATH_V1 + "/{id}";
	}
	
	@Setter
	private String host;
	
	private final RestTemplate restTemplate;

	public BreweryBeerClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public BeerDto getBeerById(UUID uuid) {
		return restTemplate.getForObject(host + SINGLE_BEER_PATH_V1, BeerDto.class, uuid);
	}
	
	public URI saveBeer(BeerDto beer) {
		return restTemplate.postForLocation(host + BEER_COLLECTION_PATH_V1, beer);
	}

	public void updateBeer(UUID uuid, BeerDto beer) {
		restTemplate.put(host + SINGLE_BEER_PATH_V1, beer, uuid);
	}
	
	public void deleteBeer(UUID uuid) {
		restTemplate.delete(host + SINGLE_BEER_PATH_V1, uuid);
	}
	
}
