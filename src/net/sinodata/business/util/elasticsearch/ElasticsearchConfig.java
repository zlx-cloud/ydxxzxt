package net.sinodata.business.util.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import net.sinodata.business.entity.ConfigInfo;

@Configuration
public class ElasticsearchConfig {

	@Autowired(required = false)
	private ConfigInfo configInfo;

	@Bean
	public ElasticsearchClient elasticsearchClient() {
		ElasticsearchClient client = new ElasticsearchClient(null);

		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
				configInfo.getUsername(), configInfo.getPassword()));

		RestClient restClient = RestClient.builder(new HttpHost(configInfo.getIp(), configInfo.getPort()))
				.setHttpClientConfigCallback(
						httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider))
				.build();

		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

		client = new ElasticsearchClient(transport);

		return client;

	}

}