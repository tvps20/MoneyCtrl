import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ModalLancamentoComponent } from './modal-lancamento/modal-lancamento.component';
import { ModalAvisoComponent } from './modal-aviso/modal-aviso.component';
import { FormDebugComponent } from './form-debug/form-debug.component';
import { InputFieldComponent } from './input-field/input-field.component';
import { SharedImportModule } from './../imports/shared-import.module';
import { ModalPagamentoComponent } from './modal-pagamento/modal-pagamento.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule
  ],
  declarations: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent,
    ModalLancamentoComponent,
    ModalAvisoComponent,
    FormDebugComponent,
    InputFieldComponent,
    ModalPagamentoComponent
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent,
    ModalLancamentoComponent,
    ModalAvisoComponent,
    FormDebugComponent,
    InputFieldComponent,
    ModalPagamentoComponent
  ]
})
export class ComponentsModule { }
