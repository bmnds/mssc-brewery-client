package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.springframework.msscbreweryclient.web.model.Customer;
import lombok.Setter;

/**
 * Created to exercise the construction of a Reactive Client using Web Flux.
 * 
 * @author Bruno
 *
 */
@Component
@ConfigurationProperties(prefix = "sfg.brewery.api", ignoreUnknownFields = true)
public class BreweryCustomerWebClient {

	@Setter
	private String customers;

	private final WebClient webClient;

	public BreweryCustomerWebClient(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder
				.build();
	}

	public Customer getCustomerById(UUID uuid) {
		return webClient.get()
				.uri(customers + "/{id}", uuid)
				.retrieve().bodyToMono(Customer.class).block();
	}

	public URI saveCustomer(Customer customer) {
		return webClient.post()
				.uri(customers).bodyValue(customer)
				.retrieve().toBodilessEntity().block()
				.getHeaders().getLocation();
	}

	public void updateCustomer(UUID uuid, Customer customer) {
		webClient.put()
				.uri(customers + "/{id}", uuid).bodyValue(customer)
				.retrieve().toBodilessEntity().block();
	}

	public void deleteCustomer(UUID uuid) {
		webClient.delete()
				.uri(customers + "/{id}", uuid)
				.exchange().block()
				.releaseBody().block();
	}

}
