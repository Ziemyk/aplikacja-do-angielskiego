import {css, customElement, html, LitElement} from "lit-element";
import {ChildPart} from "lit";

class MyVaadinComponent extends LitElement {

   handleButtonClick(event: MouseEvent) {
        const button = event.target as HTMLButtonElement;
        const key = button.textContent;
        console.log(`Klawisz ${key} został naciśnięty!`);

    }
    static get styles() {
        return css`
      :host {
        display: flex;
        flex-wrap: wrap;
        gap: 4px;
        padding: 4px;
        background-color: transparent;
      } 
          .row-2  {
            margin-left: 30px;
            margin-bottom: 3px;
            margin-top: 3px;
          }

          .row-3  {
            margin-left: 60px;
          }
      button {
        font-size: 20px;
        padding: 8px 16px;
        background-color: #fff;
        border: none;
        border-radius: 4px;
        box-shadow: 2px 2px 2px rgba(0, 0, 0, 0.2);
        cursor: pointer;
        transition: background-color 0.2s ease-in-out;
        font-family: "Comic Sans MS";
        font-weight: bold;
      }

      button:hover {
        background-color: #f0f0f0;
      }

      button:active {
        box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
        transform: translate(1px, 1px);
      }

      button:focus {
        outline: none;
        //background-color: transparent;
      }

      button[data-key=","], button[data-key="."], button[data-key="-"] {
        grid-column: span 2;
      }
    `;
    }

    render() {
        return html`
<div class="virtual-keyboard" id="keyboard">
 <div class="row row-1">
  <button class="key" text="Q" id="button0">Q</button>
  <button class="key" text="w" id="button1">W</button>
  <button class="key" id="button2">E</button>
  <button class="key" id="button3">R</button>
  <button class="key" id="button4">T</button>
  <button class="key" id="button5">Y</button>
  <button class="key" id="button6">U</button>
  <button class="key" id="button7">I</button>
  <button class="key" id="button8">O</button>
  <button class="key" id="button9">P</button>
 </div>
 <div class="row row-2">
  <button class="key" id="button10">A</button>
  <button class="key" id="button11">S</button>
  <button class="key" id="button12">D</button>
  <button class="key" id="button13">F</button>
  <button class="key" id="button14">G</button>
  <button class="key" id="button15">H</button>
  <button class="key" id="button16">J</button>
  <button class="key" id="button17">K</button>
  <button class="key" id="button18">L</button>
 </div>
 <div class="row row-3">
  <button class="key" id="button19">Z</button>
  <button class="key" id="button20">X</button>
  <button class="key" id="button21">C</button>
  <button class="key" id="button22">V</button>
  <button class="key" id="button23">B</button>
  <button class="key" id="button24">N</button>
  <button class="test" id="button25">M</button>
 </div>
</div>
`;

    }


}
//const myVaadinComponent = new MyVaadinComponent();
customElements.define('my-vaadin-component', MyVaadinComponent);
//
//document.body.appendChild(myVaadinComponent);

