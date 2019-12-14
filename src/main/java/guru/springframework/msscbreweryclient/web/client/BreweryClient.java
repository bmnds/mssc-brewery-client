package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import guru.springframework.msscbreweryclient.web.model.Customer;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {
	
	private final String BEER_PATH_V1 = "/api/v1/beer/";
	private final String CUSTOMER_PATH_V1 = "/api/v1/customer/";
	
	@Setter
	private String apiHost;
	
	private final RestTemplate restTemplate;

	public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public BeerDto getBeerById(UUID uuid) {
		return restTemplate.getForObject(apiHost + BEER_PATH_V1 + uuid, BeerDto.class);
	}
	
	public URI saveBeer(BeerDto beer) {
		return restTemplate.postForLocation(apiHost + BEER_PATH_V1, beer);
	}

	public Customer getCustomerById(UUID uuid) {
		return restTemplate.getForObject(apiHost + CUSTOMER_PATH_V1 + uuid, Customer.class);
	}
	
	public URI saveCustomer(Customer customer) {
		return restTemplate.postForLocation(apiHost + CUSTOMER_PATH_V1, customer);
	}

}
