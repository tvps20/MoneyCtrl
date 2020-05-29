import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { UserCompradorComponent } from './user-comprador.component';

import { SharedImportModule } from 'src/app/shared-import/shared-import.module';


@NgModule({
  declarations: [
    UserCompradorComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule
  ]
})
export class UserCompradorModule { }
