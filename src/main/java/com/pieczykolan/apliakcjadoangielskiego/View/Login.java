package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.Layout.MainLayout;
import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "", layout = MainLayout.class)
@CssImport("themes/my-theme/style.css")
public class Login extends VerticalLayout {
    @Autowired
    public Login(AuthService authService) {
        HorizontalLayout horizontalLayoutLogin = new HorizontalLayout();
        HorizontalLayout horizontalLayoutRegisterAndCheckBox = new HorizontalLayout();
        horizontalLayoutRegisterAndCheckBox.addClassName("horizontalLayoutRegisterAndCheckBox");
        LoginForm loginForm = new LoginForm();
        loginForm.addClassName("loginForm");
        Checkbox checkboxTeacher = new Checkbox("log in as a teacher");
        checkboxTeacher.addClassName("checkboxTeacher");

        //loginForm.setError(true);
        //loginForm.setAction("Login");
        loginForm.setForgotPasswordButtonVisible(false);
        horizontalLayoutLogin.add(loginForm);
        loginForm.addLoginListener(event ->{
           if(!checkboxTeacher.getValue()) {
               try {
                   authService.authenticateAsStudent(event.getUsername(), event.getPassword());
                   UI.getCurrent().getPage().setLocation("EnglishStudy");

               } catch (AuthService.AuthException e) {
                   Notification.show("Wrong password or username");
               }
           }else{
               try {
                   authService.authenticateAsTeacher(event.getUsername(), event.getPassword());
                   UI.getCurrent().getPage().setLocation("TeacherView");

               } catch (AuthService.AuthException e) {
                   Notification.show("Wrong password or username");
               }
           }
        });
        RouterLink routerLinkRegister = new RouterLink("Register", Register.class);
        routerLinkRegister.addClassName("router-link");
        horizontalLayoutRegisterAndCheckBox.add(checkboxTeacher,routerLinkRegister);
        add(horizontalLayoutLogin,horizontalLayoutRegisterAndCheckBox);
    }
}
