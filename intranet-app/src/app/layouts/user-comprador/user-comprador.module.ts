import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserCompradorComponent } from './user-comprador.component';
import { SharedImportModule } from 'src/app/shared-import/shared-import.module';


@NgModule({
  declarations: [
    UserCompradorComponent
  ],
  imports: [
    CommonModule,
    SharedImportModule
  ],
  exports: [
    UserCompradorComponent
  ]
})
export class UserCompradorModule { }
