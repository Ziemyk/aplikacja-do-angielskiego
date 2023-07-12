package com.pieczykolan.apliakcjadoangielskiego.Component;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.polymertemplate.Id;

@Tag("time-counter")
@JsModule("./timeCounter.js")
public class SecondsCounter extends LitTemplate {

    @Id("counter")
    private Span counter;

    private int seconds;
    private boolean running;

    public SecondsCounter() {
        seconds = 16;
        running = false;
    }

//    @Override
//    protected void onAttach(AttachEvent attachEvent) {
//        super.onAttach(attachEvent);
//        startCounter();
//    }

//    @Override
//    protected void onDetach(DetachEvent detachEvent) {
//        super.onDetach(detachEvent);
//        stopCounter();
//    }

    public void startCounter() {
        //counter.getElement().executeJs()
        running = true;
        new Thread(() -> {
            while (running) {
                try {
                    Thread.sleep(1000);
                    seconds--;
                    getUI().ifPresent(ui -> ui.access(() -> counter.setText("Pozostało " + String.valueOf(seconds) + " sekund")));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void stopCounter() {
        running = false;
    }

    public int getSeconds() {
        return seconds;
    }
    public void restartCounter(){
        seconds = 16;

    }

    public void addCounterStopListener(ComponentEventListener<CounterStopEvent> listener) {
        addListener(CounterStopEvent.class, listener);
    }
    public static class CounterStopEvent extends ComponentEvent<SecondsCounter> {
        public CounterStopEvent(SecondsCounter source, boolean fromClient) {
            super(source, fromClient);
        }
    }
}
