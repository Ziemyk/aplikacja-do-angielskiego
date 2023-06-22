package com.pieczykolan.apliakcjadoangielskiego.View;

import com.pieczykolan.apliakcjadoangielskiego.MainView.KeyboardComponent;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.template.Id;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

@Route(value = "")
@CssImport("themes/my-theme/startPageStyle.css")
public class StartPage extends VerticalLayout {
    private Button buttonLogin;
    private Button buttonRegister;
    private H1 viewTitle ;
    private TextArea textArea;
    private TextArea textArea2  ;
    private AppLayout appLayout;
    private Div div;
    private HorizontalLayout horizontalLayout;
    private Image firstImage = new Image();
    private Image secondImage = new Image();

    public StartPage() {
        appLayout = new AppLayout();
        viewTitle = new H1("English Study");
        buttonLogin = new Button("Log In");
        buttonLogin.addClickListener(e-> UI.getCurrent().getPage().setLocation("Login"));
        buttonRegister = new Button("Register");
        buttonRegister.addClickListener(e -> UI.getCurrent().getPage().setLocation("Register"));
        viewTitle.add(buttonLogin,buttonRegister);
        textArea = new TextArea("Info");
        textArea2 = new TextArea("Info");
        horizontalLayout = new HorizontalLayout(textArea, textArea2);
        setClassName();
        appLayout.addToNavbar(viewTitle);
        add(appLayout,horizontalLayout);
        // tu napewno do ustawienia widoku przyda się ten board
    }
    public void setClassName(){
        buttonLogin.addClassName("button-login");
        buttonRegister.addClassName("button-register");;
        viewTitle.setClassName("viewTitle");
        horizontalLayout.addClassName("horizontal-layout");
        textArea2.addClassName("text-area2");
        textArea.addClassName("text-area1");



    }

}

