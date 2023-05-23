import { LitElement, html, css, customElement } from 'lit-element';
import '@vaadin/button/src/vaadin-button.js';

@customElement('my-view')
export class MyView extends LitElement {
  static get styles() {
    return css`
      :host {
          display: block;
          height: 100%;
      }
      `;
  }

  render() {
    return html`
<vaadin-button>
  Button 
</vaadin-button>
<vaadin-button id="vaadinButton" tabindex="0">
  Button 
</vaadin-button>
<vaadin-button tabindex="0">
  Button 
</vaadin-button>
`;
  }

  // Remove this method to render the contents of this view inside Shadow DOM
  createRenderRoot() {
    return this;
  }
}
