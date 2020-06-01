import { NgModule, Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { LoginRoutes } from './login.routing';

import { SharedImportModule } from '../shared-import/shared-import.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(LoginRoutes),
    SharedImportModule
  ],
  declarations: []
})
export class LoginModule { }
