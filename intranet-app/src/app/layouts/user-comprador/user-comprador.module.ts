import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { UserCompradorComponent } from './user-comprador.component';
import { CompradorDetailComponent } from './comprador-detail/comprador-detail.component';

import { SharedImportModule } from 'src/app/shared-import/shared-import.module';


@NgModule({
  declarations: [
    UserCompradorComponent,
    CompradorDetailComponent
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
