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
    private Button buttonAboutUs;
    private H1 viewTitle ;
    private TextArea textArea;
    private TextArea textArea2  ;
    private AppLayout appLayout;
    private Div div;
    private HorizontalLayout horizontalLayout;


    private HtmlComponent htmlComponent;

    public StartPage() {
        appLayout = new AppLayout();
        viewTitle = new H1("English Study");
        buttonLogin = new Button("Log In");
        buttonLogin.addClassName("button-login");
        buttonLogin.addClickListener(e-> UI.getCurrent().getPage().setLocation("Login"));
        buttonRegister = new Button("Register");
        buttonRegister.addClassName("button-register");
        buttonRegister.addClickListener(e -> UI.getCurrent().getPage().setLocation("Register"));
        buttonAboutUs = new Button("test");
        //buttonAboutUs.addClickListener( e -> keyboardComponent.test());
        viewTitle.add(buttonLogin,buttonRegister,buttonAboutUs);
        textArea = new TextArea("Info");
        textArea.addClassName("text-area1");
        textArea2 = new TextArea("Info");
        textArea2.addClassName("text-area2");

        horizontalLayout = new HorizontalLayout(textArea, textArea2);
        horizontalLayout.addClassName("horizontal-layout");
        appLayout.addToNavbar(viewTitle);
        add(appLayout,horizontalLayout);



        //addToNavbar(viewTitle)
        //addToDrawer(textArea ,textArea2);
        //IFrame frame = new IFrame("frontend/keyboard.html");

        //StreamResource htmlResource;
       // htmlResource = new StreamResource("frontend/keyboard.html",
              //  () -> getClass().getResourceAsStream("frontend/keyboard.html"));
        //HtmlContainer htmlContainer = new HtmlContainer("iframe");
       // htmlContainer.getElement().setAttribute("src",htmlResource);



        //HtmlComponent virtualKeyboard = new HtmlComponent("div");
        //virtualKeyboard.setHeight("300px");
       // virtualKeyboard.setWidth("500px");
        //virtualKeyboard.getElement().setProperty("src","jetbrains://idea/navigate/reference?project=apliakcja-do-angielskiego&path=frontend/keyboard.html");
        //div = new Div(virtualKeyboard);
       // add(virtualKeyboard);

    }

}

