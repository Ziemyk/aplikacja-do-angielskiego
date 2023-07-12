package com.pieczykolan.apliakcjadoangielskiego.Component;


import com.pieczykolan.apliakcjadoangielskiego.GameService.GameLogic;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.template.Id;
import java.util.ArrayList;
import java.util.List;

@Tag("my-vaadin-component")
@JsModule("./virtualKeyboard.ts")
public class KeyboardComponent extends LitTemplate {

    GameLogic gameLogic;
    UI ui = UI.getCurrent();
    private List<NativeButton> buttons = new ArrayList<>();


    @Id("button0")
    private NativeButton button0;
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
    public int test = 0;
    public KeyboardComponent(GameLogic gameLogic) {
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
        buttons.add(button0);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        buttons.add(button7);
        buttons.add(button8);
        buttons.add(button9);
        buttons.add(button10);
        buttons.add(button11);
        buttons.add(button12);
        buttons.add(button13);
        buttons.add(button14);
        buttons.add(button15);
        buttons.add(button16);
        buttons.add(button17);
        buttons.add(button18);
        buttons.add(button19);
        buttons.add(button20);
        buttons.add(button21);
        buttons.add(button22);
        buttons.add(button23);
        buttons.add(button24);
        buttons.add(button25);
        this.gameLogic = gameLogic;
        addAttachListener(attachEvent -> {
            // view is loaded at this point. You can for example execute javascript which relies on certain elements being in the page
            for (int i=0;i<26;i++) {
                buttons.get(i).addClickListener(e -> checkLetter(e.getSource()));
                buttons.get(i).addClickListener(e -> System.out.println(e.getSource()));
            }
        });
   }

    private void checkLetter(NativeButton button) {
        String letter = button.getText();
        button.setEnabled(false);
        gameLogic.checkLetter(letter);


    }
    public void restartKeyboard(){
        for (NativeButton b: buttons) {
            b.setEnabled(true);
        }
    }
    // TODO ostatni wciśniety przcisk przed zgadnięciem sie nie odswieza


}
