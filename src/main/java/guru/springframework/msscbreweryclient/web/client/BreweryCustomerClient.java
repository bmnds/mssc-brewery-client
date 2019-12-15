package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.Customer;

@Component
public class BreweryCustomerClient {

	private final String customers;
	private final RestTemplate restTemplate;

	public BreweryCustomerClient(RestTemplateBuilder restTemplateBuilder,
			@Value("${sfg.brewery.api.host}") String host,
			@Value("${sfg.brewery.api.version}") String version,
			@Value("${sfg.brewery.api.customers}") String customers) {
		this.customers = customers;
		this.restTemplate = restTemplateBuilder
				.rootUri(host + version)
				.build();
	}

	public Customer getCustomerById(UUID uuid) {
		return restTemplate.getForObject(customers + "/{id}", Customer.class, uuid);
	}

	public URI saveCustomer(Customer customer) {
		return restTemplate.postForLocation(customers, customer);
	}

	public void updateCustomer(UUID uuid, Customer customer) {
		restTemplate.put(customers + "/{id}", customer, uuid);
	}

	public void deleteCustomer(UUID uuid) {
		restTemplate.delete(customers + "/{id}", uuid);
	}

}
