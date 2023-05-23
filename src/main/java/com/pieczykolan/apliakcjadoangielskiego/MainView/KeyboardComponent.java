package com.pieczykolan.apliakcjadoangielskiego.MainView;

import com.pieczykolan.apliakcjadoangielskiego.Services.AuthService;
import com.pieczykolan.apliakcjadoangielskiego.Services.GameLogic;
import com.pieczykolan.apliakcjadoangielskiego.View.Game;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Native;

@Tag("my-vaadin-component")
@JsModule("./virtualKeyboard.ts")
public class KeyboardComponent extends LitTemplate {

    GameLogic gameLogic;
    UI ui = UI.getCurrent();

    @Id("button1")
    public NativeButton button1;
    @Id("button2")
    public NativeButton button2;
    @Id("button3")
    public NativeButton button3;
    @Id("button4")
    public NativeButton button4;
    @Id("button5")
    public NativeButton button5;
    @Id("button6")
    public NativeButton button6;
    @Id("button7")
    public NativeButton button7;
    @Id("button8")
    public NativeButton button8;
    @Id("button9")
    public NativeButton button9;
    @Id("button10")
    public NativeButton button10;
    @Id("button11")
    public NativeButton button11;
    @Id("button12")
    public NativeButton button12;
    @Id("button13")
    public NativeButton button13;
    @Id("button14")
    public NativeButton button14;
    @Id("button15")
    public NativeButton button15;
    @Id("button16")
    public NativeButton button16;
    @Id("button17")
    public NativeButton button17;
    @Id("button18")
    public NativeButton button18;
    @Id("button19")
    public NativeButton button19;
    @Id("button20")
    public NativeButton button20;
    @Id("button21")
    public NativeButton button21;
    @Id("button22")
    public NativeButton button22;
    @Id("button23")
    public NativeButton button23;
    @Id("button24")
    public NativeButton button24;
    @Id("button25")
    public NativeButton button25;
    @Id("button0")
    private NativeButton button0;

    public KeyboardComponent(GameLogic gameLogic) {

        this.gameLogic = gameLogic;
        addAttachListener(attachEvent -> {
            // view is loaded at this point. You can for example execute javascript which relies on certain elements being in the page
            button0.setText("Q");
            button1.setText("W");
            button2.setText("E");
            button3.setText("R");
            button4.setText("T");
            button5.setText("Y");
            button6.setText("U");
            button7.setText("I");
            button8.setText("O");
            button9.setText("P");
            button10.setText("A");
            button11.setText("S");
            button12.setText("D");
            button13.setText("F");
            button14.setText("G");
            button15.setText("H");
            button16.setText("J");
            button17.setText("K");
            button18.setText("L");
            button19.setText("Z");
            button20.setText("X");
            button21.setText("C");
            button22.setText("V");
            button23.setText("B");
            button24.setText("N");
            button25.setText("M");
            button0.addClickListener(e -> checkLetter(button0.getText()));
            button1.addClickListener(e -> checkLetter(button1.getText()));
            button2.addClickListener(e -> checkLetter(button2.getText()));
            button3.addClickListener(e -> checkLetter(button3.getText()));
            button4.addClickListener(e -> checkLetter(button4.getText()));
            button5.addClickListener(e -> checkLetter(button5.getText()));
            button6.addClickListener(e -> checkLetter(button6.getText()));
            button7.addClickListener(e -> checkLetter(button7.getText()));
            button8.addClickListener(e -> checkLetter(button8.getText()));
            button9.addClickListener(e -> checkLetter(button9.getText()));
            button10.addClickListener(e -> checkLetter(button10.getText()));
            button11.addClickListener(e -> checkLetter(button11.getText()));
            button12.addClickListener(e -> checkLetter(button12.getText()));
            button13.addClickListener(e -> checkLetter(button13.getText()));
            button14.addClickListener(e -> checkLetter(button14.getText()));
            button15.addClickListener(e -> checkLetter(button15.getText()));
            button16.addClickListener(e -> checkLetter(button16.getText()));
            button17.addClickListener(e -> checkLetter(button17.getText()));
            button18.addClickListener(e -> checkLetter(button18.getText()));
            button19.addClickListener(e -> checkLetter(button19.getText()));
            button20.addClickListener(e -> checkLetter(button20.getText()));
            button21.addClickListener(e -> checkLetter(button21.getText()));
            button22.addClickListener(e -> checkLetter(button22.getText()));
            button23.addClickListener(e -> checkLetter(button23.getText()));
            button24.addClickListener(e -> checkLetter(button24.getText()));
            button25.addClickListener(e -> checkLetter(button25.getText()));



        });
   }

    private void checkLetter(String letter) {
        gameLogic.checkLetter(letter);
        System.out.println(letter);


    }


}
