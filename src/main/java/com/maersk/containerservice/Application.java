package com.maersk.containerservice;

import io.netty.channel.ChannelOption;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.http.client.HttpClient;

import java.nio.file.Path;
import java.time.Duration;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * This is necessary to have the Spring Boot app use the Astra secure bundle
	 * to connect to the database
	 */
	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}


	@Bean
	public LocalValidatorFactoryBean validator() {return new LocalValidatorFactoryBean();}

	@Bean
	public WebClient webClient (WebClient.Builder builder){

		ConnectionProvider provider = ConnectionProvider.builder("custom")
				.maxConnections(1)
				.maxIdleTime(Duration.ofSeconds(10000))
				.maxLifeTime(Duration.ofSeconds(10000))
				.pendingAcquireTimeout(Duration.ofSeconds(10000))
				.evictInBackground(Duration.ofSeconds(10000))
				.build();

		HttpClient httpClient = HttpClient
				.create(provider)
				.resolver(DefaultAddressResolverGroup.INSTANCE)
				.keepAlive(true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int)10)
				.responseTimeout(Duration.ofMillis(10000));

		return builder.baseUrl("")
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}

}
