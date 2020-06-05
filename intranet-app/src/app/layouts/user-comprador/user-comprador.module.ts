import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { UserCompradorComponent } from './user-comprador.component';
import { CompradorDetailComponent } from './comprador-detail/comprador-detail.component';

import { SharedImportModule } from 'src/app/shared/imports/shared-import.module';
import { ComponentsModule } from 'src/app/shared/components/components.module';



@NgModule({
  declarations: [
    UserCompradorComponent,
    CompradorDetailComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule,
    ComponentsModule
  ],
  exports: [
    UserCompradorComponent,
    CompradorDetailComponent
  ]
})
export class UserCompradorModule { }
