import { NgModule, LOCALE_ID } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
registerLocaleData(localePt);

import { AppRoutingModule } from './app.routing';
import { ComponentsModule } from './shared/components/components.module';

import { AppComponent } from './app.component';

import { BaseLayoutComponent } from './layouts/base-layout.component';
import { LoginComponent } from './login/login.component';
import { SharedImportModule } from './shared/imports/shared-import.module';


@NgModule({
  declarations: [
    AppComponent,
    BaseLayoutComponent,
    LoginComponent
  ],
  imports: [
    BrowserAnimationsModule,
    FormsModule,
    RouterModule,
    AppRoutingModule,
    HttpClientModule,
    ComponentsModule,
    SharedImportModule
  ],
  providers: [
      { provide: LOCALE_ID, useValue: 'pt-BR' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
