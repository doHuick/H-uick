package com.dohit.huick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.dohit.huick.global.property.AppProperties;
import com.dohit.huick.global.property.CorsProperties;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
	CorsProperties.class,
	AppProperties.class
})
public class HuickApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuickApplication.class, args);
	}

}
