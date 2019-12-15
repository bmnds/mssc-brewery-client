package guru.springframework.msscbreweryclient.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Component
@Log4j2
@ConfigurationProperties(prefix = "sfg.brewery.api", ignoreUnknownFields = true)
public class NIOWebClientCustomizer implements WebClientCustomizer {

	@Setter
	private String host;
	@Setter
	private String version;

	@Override
	public void customize(WebClient.Builder webClientBuilder) {
		if (log.isDebugEnabled()) {
			log.debug("Customizing {}", webClientBuilder);
		}

		webClientBuilder
				.baseUrl(host + version)
				.filter(this.loggingRequestFilter())
				.filter(this.loggingResponseFilter());
	}

	private ExchangeFilterFunction loggingRequestFilter() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			if (log.isDebugEnabled()) {
				log.debug(
						"===========================request begin================================================");
				log.debug("URI         : {}", clientRequest.url());
				log.debug("Method      : {}", clientRequest.method());
				log.debug("Headers     : {}", clientRequest.headers().entrySet());
//				log.debug("Request body: {}", new String(body, "UTF-8"));
				log.debug("NO ACCESS TO REQUEST BODY IN WEB FLUX");
				log.debug("==========================request end================================================");
			}
			return Mono.just(clientRequest);
		});
	}

	private ExchangeFilterFunction loggingResponseFilter() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			if (log.isDebugEnabled()) {
				log.debug("============================response begin==========================================");
				log.debug("Status code  : {}", clientResponse.statusCode());
				log.debug("Headers      : {}", clientResponse.headers().asHttpHeaders());
//				log.debug("Response body: {}", clientResponse.StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
				log.debug("NO ACCESS TO RESPONSE BODY IN WEB FLUX");
				log.debug("=======================response end=================================================");
			}
			return Mono.just(clientResponse);
		});

	}

}
