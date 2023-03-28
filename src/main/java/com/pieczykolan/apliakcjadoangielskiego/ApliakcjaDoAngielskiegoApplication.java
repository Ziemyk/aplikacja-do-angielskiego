package com.pieczykolan.apliakcjadoangielskiego;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.pieczykolan.apliakcjadoangielskiego.Services.AuthServices"})
public class ApliakcjaDoAngielskiegoApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ApliakcjaDoAngielskiegoApplication.class, args);

	}

}
