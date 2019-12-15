package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.Customer;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "sfg.brewery.api", ignoreUnknownFields = true)
public class BreweryCustomerClient {

	@Setter
	private String host;
	@Setter
	private String version;
	@Setter
	private String customers;

	private final RestTemplate restTemplate;

	private String getCustomersCollectionEndpoint() {
		return host + version + customers;
	}
	
	private String getSingleCustomerEndpoint() {
		return host + version + customers + "/{id}";
	}

	public BreweryCustomerClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public Customer getCustomerById(UUID uuid) {
		return restTemplate.getForObject(this.getSingleCustomerEndpoint(), Customer.class, uuid);
	}

	public URI saveCustomer(Customer customer) {
		return restTemplate.postForLocation(this.getCustomersCollectionEndpoint(), customer);
	}

	public void updateCustomer(UUID uuid, Customer customer) {
		restTemplate.put(this.getSingleCustomerEndpoint(), customer, uuid);
	}

	public void deleteCustomer(UUID uuid) {
		restTemplate.delete(this.getSingleCustomerEndpoint(), uuid);
	}

}
