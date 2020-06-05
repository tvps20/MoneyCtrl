import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { UserCompradorComponent } from './user-comprador.component';
import { CompradorDetailComponent } from './comprador-detail/comprador-detail.component';
import { FormDebugComponent } from 'src/app/shared/components/form-debug/form-debug.component';
import { InputFieldComponent } from 'src/app/shared/components/input-field/input-field.component';

import { SharedImportModule } from 'src/app/shared/imports/shared-import.module';



@NgModule({
  declarations: [
    UserCompradorComponent,
    CompradorDetailComponent,
    FormDebugComponent,
    InputFieldComponent
  ],
  imports: [
    CommonModule,
    SharedImportModule,
    RouterModule
  ],
  exports: [
    UserCompradorComponent,
    CompradorDetailComponent
  ]
})
export class UserCompradorModule { }
