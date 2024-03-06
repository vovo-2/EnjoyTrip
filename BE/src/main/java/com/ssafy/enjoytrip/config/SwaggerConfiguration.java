package com.ssafy.enjoytrip.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {
	private final ServerProperties serverProperties;

	public SwaggerConfiguration(ServerProperties serverProperties) {
		super();
		this.serverProperties = serverProperties;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.OAS_30).consumes(getConsumeContentTypes())
				.produces(getProduceContentTypes()).groupName("API").apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.enjoytrip.controller")).paths(PathSelectors.any())
				.build().useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("[서울_18반]EnjoyTrip_04조").description("<h2>API</h2>").version("1.0").build();
	}

	private Set<String> getConsumeContentTypes() {
		Set<String> consumes = new HashSet<>();
		consumes.add("application/json;charset=UTF-8");
//      consumes.add("application/xml;charset=UTF-8");
		consumes.add("application/x-www-form-urlencoded");
		return consumes;
	}

	private Set<String> getProduceContentTypes() {
		Set<String> produces = new HashSet<>();
		produces.add("application/json;charset=UTF-8");
		return produces;
	}
}
