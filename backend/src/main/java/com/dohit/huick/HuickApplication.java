package com.dohit.huick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.dohit.huick.global.property.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
public class HuickApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuickApplication.class, args);
	}

}
