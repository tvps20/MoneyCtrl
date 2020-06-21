import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DividaComponent } from './divida.component';
import { ModalDividaComponent } from './modal-divida/modal-divida.component';

import { SharedImportModule } from 'src/app/shared/imports/shared-import.module';
import { ComponentsModule } from 'src/app/shared/components/components.module';


@NgModule({
  declarations: [
    DividaComponent,
    ModalDividaComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule,
    ComponentsModule
  ],
  exports: [
    DividaComponent,
    ModalDividaComponent
  ]
})
export class DividaModule { }
