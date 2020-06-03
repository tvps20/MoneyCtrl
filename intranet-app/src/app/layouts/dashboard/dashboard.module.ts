import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { DashboardComponent } from './dashboard.component';

import { SharedImportModule } from 'src/app/shared/imports/shared-import.module';


@NgModule({
  declarations: [
    DashboardComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    SharedImportModule
  ],
  exports: [
    DashboardComponent
  ]
})
export class DashboardModule { }
