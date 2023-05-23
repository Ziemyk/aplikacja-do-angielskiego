import { html, PolymerElement } from '@polymer/polymer/polymer-element.js';

class TimeCounter extends PolymerElement {
    static get template() {
        return html`
      <style>
        :host {
          display: block;
        }
      </style>
      <span id="counter">Czas</span>
    `;
    }

    static get is() {
        return 'time-counter';
    }
}

customElements.define(TimeCounter.is, TimeCounter);
