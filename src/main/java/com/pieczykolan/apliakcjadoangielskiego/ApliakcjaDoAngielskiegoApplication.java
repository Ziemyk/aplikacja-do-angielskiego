package com.pieczykolan.apliakcjadoangielskiego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.pieczykolan.apliakcjadoangielskiego.Services.AuthServices"})
public class ApliakcjaDoAngielskiegoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApliakcjaDoAngielskiegoApplication.class, args);

	}

}
