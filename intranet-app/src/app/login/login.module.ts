import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { LoginRoutes } from './login.routing';


@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(LoginRoutes)
  ],
  declarations: []
})
export class LoginModule { }
