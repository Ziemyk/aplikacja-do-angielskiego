package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.MainView.MainLayout;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "Login", layout = MainLayout.class)
@CssImport("themes/my-theme/style.css")
public class Login extends VerticalLayout {
    @Autowired
    public Login(AuthService authService) {
        HorizontalLayout horizontalLayoutLogin = new HorizontalLayout();
        LoginForm loginForm = new LoginForm();
        //loginForm.setError(true);
        //loginForm.setAction("Login");
        loginForm.addClassName("loginform");
        horizontalLayoutLogin.add(loginForm);
        loginForm.addLoginListener(event ->{
           try{
               authService.authenticate(event.getUsername(),event.getPassword());
               UI.getCurrent().getPage().setLocation("EnglishStudy");

           }catch (AuthService.AuthException e){
                Notification.show("Wrong password or username");
            }
        });
        RouterLink routerLinkRegister = new RouterLink("Register", Register.class);
        routerLinkRegister.addClassName("router-link");
        add(horizontalLayoutLogin,routerLinkRegister);
    }
}
