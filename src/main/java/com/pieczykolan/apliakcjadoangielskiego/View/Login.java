package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "Login", layout = MainLayout.class)

public class Login extends VerticalLayout {
    @Autowired
    public Login(AuthService authService) {

        LoginForm loginForm = new LoginForm();
        //loginForm.setError(true);
        loginForm.setAction("Login");
        loginForm.addClassName("loginform");

        loginForm.addLoginListener(event ->{
           try{
               authService.authenticate(event.getUsername(),event.getPassword());
               UI.getCurrent().getPage().setLocation("EnglishStudy");

           }catch (AuthService.AuthException e){
                Notification.show("Wrong password or username");
            }
        });
        RouterLink routerLinkRegister = new RouterLink("Register", Register.class);
        add(loginForm,routerLinkRegister);
    }
}
