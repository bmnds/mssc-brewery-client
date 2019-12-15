package guru.springframework.msscbreweryclient.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class BlockingRestTemplateCustomizer implements RestTemplateCustomizer {

	@Override
	public void customize(RestTemplate restTemplate) {
		if (log.isDebugEnabled()) {
			log.debug("Customizing {}", restTemplate);
		}

		restTemplate.setRequestFactory(this.clientHttpRequestFactory());
		restTemplate.getInterceptors().add(this.clientHttpRequestInterceptor());
	}

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

		// Encapsulates RequestFactory into Buffering wrapper to enable body logging
		return new BufferingClientHttpRequestFactory(
				new HttpComponentsClientHttpRequestFactory(httpClient));
	}

	private ClientHttpRequestInterceptor clientHttpRequestInterceptor() {
		return new ClientHttpRequestInterceptor() {

			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				this.logRequest(request, body);
				ClientHttpResponse response = execution.execute(request, body);
				this.logResponse(response);
				return response;
			}

			private void logRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {
				if (log.isDebugEnabled()) {
					log.debug(
							"===========================request begin================================================");
					log.debug("URI         : {}", request.getURI());
					log.debug("Method      : {}", request.getMethod());
					log.debug("Headers     : {}", request.getHeaders());
					log.debug("Request body: {}", new String(body, "UTF-8"));
					log.debug("==========================request end================================================");
				}
			}

			private void logResponse(ClientHttpResponse response) throws IOException {
				if (log.isDebugEnabled()) {
					log.debug("============================response begin==========================================");
					log.debug("Status code  : {}", response.getStatusCode());
					log.debug("Status text  : {}", response.getStatusText());
					log.debug("Headers      : {}", response.getHeaders());
					log.debug("Response body: {}",
							StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
					log.debug("=======================response end=================================================");
				}
			}

		};
	}

}
