package guru.springframework.msscbreweryclient.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(20);

		RequestConfig requestConfig = RequestConfig
				.custom()
				.setConnectionRequestTimeout(3000)
				.setSocketTimeout(3000)
				.build();

		CloseableHttpClient httpClient = HttpClients
				.custom()
				.setConnectionManager(connectionManager)
				.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
				.setDefaultRequestConfig(requestConfig)
				.build();

		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Override
	public void customize(RestTemplate restTemplate) {
		if (log.isDebugEnabled()) {
			log.debug("Customizing {}", restTemplate);
		}

		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
	}

}
