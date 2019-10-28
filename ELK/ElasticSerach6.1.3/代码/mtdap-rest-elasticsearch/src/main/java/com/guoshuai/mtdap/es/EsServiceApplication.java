package com.guoshuai.mtdap.es;

import com.guoshuai.mtdap.common.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("com.guoshuai.mtdap")
//@EnableAutoConfiguration
@EnableSwagger2 //不添加 页面出现弹窗错误
@Import({SwaggerConfig.class})
public class EsServiceApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EsServiceApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(EsServiceApplication.class, args);
	}

}

