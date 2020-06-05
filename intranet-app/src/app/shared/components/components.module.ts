import { SharedImportModule } from './../imports/shared-import.module';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { FooterComponent } from './footer/footer.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { ModalLancamentoComponent } from './modal-lancamento/modal-lancamento.component';
import { ModalDividaComponent } from './modal-divida/modal-divida.component';
import { ModalAvisoComponent } from './modal-aviso/modal-aviso.component';
import { FormDebugComponent } from './form-debug/form-debug.component';
import { InputFieldComponent } from './input-field/input-field.component';


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
    ModalDividaComponent,
    ModalAvisoComponent,
    FormDebugComponent,
    InputFieldComponent
  ],
  exports: [
    FooterComponent,
    NavbarComponent,
    SidebarComponent,
    ModalLancamentoComponent,
    ModalDividaComponent,
    ModalAvisoComponent,
    FormDebugComponent,
    InputFieldComponent
  ]
})
export class ComponentsModule { }
