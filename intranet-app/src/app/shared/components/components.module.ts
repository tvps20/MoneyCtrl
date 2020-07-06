import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { SharedImportModule } from './../imports/shared-import.module';
import { FormDebugComponent } from './form-debug/form-debug.component';
import { InputFieldComponent } from './input-field/input-field.component';
import { ModalAvisoComponent } from './modal-aviso/modal-aviso.component';
import { ModalPagamentoComponent } from './modal-pagamento/modal-pagamento.component';
import { NotificationStateComponent } from './notification-state/notification-state.component';


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
    ModalAvisoComponent,
    FormDebugComponent,
    InputFieldComponent,
    ModalPagamentoComponent,
    NotificationStateComponent
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent,
    ModalAvisoComponent,
    FormDebugComponent,
    InputFieldComponent,
    ModalPagamentoComponent,
    NotificationStateComponent
  ]
})
export class ComponentsModule { }
