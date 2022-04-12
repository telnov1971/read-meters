import '@vaadin/vaadin-button';
import '@vaadin/vaadin-date-picker';
import '@vaadin/vaadin-date-time-picker';
import '@vaadin/vaadin-form-layout';
import '@vaadin/vaadin-grid';
import '@vaadin/vaadin-grid/vaadin-grid-column';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout';
import '@vaadin/vaadin-split-layout';
import '@vaadin/vaadin-text-field';
import { html, LitElement } from 'lit';
import { customElement } from 'lit/decorators.js';

@customElement('master-detail-view')
export class MasterDetailView extends LitElement {
  createRenderRoot() {
    // Do not use a shadow root
    return this;
  }

  render() {
    return html`<vaadin-split-layout>
      <div class="grid-wrapper">
        <vaadin-grid id="grid"></vaadin-grid>
      </div>
      <div class="editor-layout">
        <div class="editor">
          <vaadin-form-layout>
            <vaadin-text-field label="Num" id="num"></vaadin-text-field
            ><vaadin-text-field label="Object" id="object"></vaadin-text-field
            ><vaadin-text-field label="Address" id="address"></vaadin-text-field
            ><vaadin-text-field label="Type md" id="typeMD"></vaadin-text-field
            ><vaadin-text-field label="Num md" id="numMD"></vaadin-text-field
            ><vaadin-text-field label="Ratio" id="ratio"></vaadin-text-field
            ><vaadin-date-picker label="Date rm" id="dateRM"></vaadin-date-picker
            ><vaadin-text-field label="Meter" id="meter"></vaadin-text-field>
          </vaadin-form-layout>
        </div>
        <vaadin-horizontal-layout class="button-layout">
          <vaadin-button theme="primary" id="save">Save</vaadin-button>
          <vaadin-button theme="tertiary" slot="" id="cancel">Cancel</vaadin-button>
        </vaadin-horizontal-layout>
      </div>
    </vaadin-split-layout>`;
  }
}
