package com.pieczykolan.apliakcjadoangielskiego;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
//@Theme(variant = Lumo.DARK)
//

@SpringBootApplication

//@ComponentScan(basePackages = {"com.pieczykolan.apliakcjadoangielskiego.Services.AuthServices"})
public class ApliakcjaDoAngielskiegoApplication  {

	public static void main(String[] args) {
		SpringApplication.run(ApliakcjaDoAngielskiegoApplication.class, args);

	}

}
