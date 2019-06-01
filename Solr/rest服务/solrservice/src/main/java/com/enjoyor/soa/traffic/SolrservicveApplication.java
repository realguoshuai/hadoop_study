package com.enjoyor.soa.traffic;

import com.enjoyor.soa.traffic.frame.configure.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.enjoyor.soa.traffic.rest.solr")
@Import({Swagger2Config.class})
public class SolrservicveApplication  extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SolrservicveApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SolrservicveApplication.class, args);
	}

}
