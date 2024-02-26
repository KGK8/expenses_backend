package com.example.expenses.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Bean
	public com.example.expenses.utils.utils utilsBean() {
		return new com.example.expenses.utils.utils();
	}

	@Bean
	public com.example.expenses.utils.JwtUtils jwtUtils(){
		return new com.example.expenses.utils.JwtUtils();
	}

	@Bean
	public com.example.expenses.services.accountsServices accountsServices() {
		return new com.example.expenses.services.accountsServices();
	}
	
	@Bean
	public com.example.expenses.services.transactionServices transactionServices(){
		return new com.example.expenses.services.transactionServices();
	}
}
