package com.pieczykolan.apliakcjadoangielskiego.View;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.servlet.tags.form.ButtonTag;
@Route("")
public class StartPage extends VerticalLayout {
    private Label labelName ;
    private Button buttonLogin;
    private Button buttonRegister;
    private Button buttonAboutUs;
    public StartPage() {
        labelName = new Label("English Study");
        buttonLogin = new Button("Log In");
        buttonLogin.addClickListener(e-> UI.getCurrent().getPage().setLocation("Login"));
        buttonRegister = new Button("Register");
        buttonRegister.addClickListener(e -> UI.getCurrent().getPage().setLocation("Register"));
        buttonAboutUs = new Button("About Us");
        buttonAboutUs.addClickListener(e -> UI.getCurrent().getPage().setLocation("AboutUs"));
        setHorizontalComponentAlignment(Alignment.CENTER, labelName,
                buttonLogin,buttonRegister,buttonAboutUs);

        add(labelName,buttonLogin,buttonRegister,buttonAboutUs);
    }

}
