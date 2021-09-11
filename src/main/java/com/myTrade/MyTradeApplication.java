package com.myTrade;

import com.myTrade.jwt.JwtConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties(JwtConfiguration.class)
public class MyTradeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTradeApplication.class, args);
	}

}
