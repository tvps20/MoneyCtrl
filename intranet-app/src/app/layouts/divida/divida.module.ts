import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DividaComponent } from './divida.component';

import { SharedImportModule } from 'src/app/shared-import/shared-import.module';
import { ComponentsModule } from 'src/app/components/components.module';


@NgModule({
  declarations: [
    DividaComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule,
    ComponentsModule
  ],
  exports: [
    DividaComponent
  ]
})
export class DividaModule { }
