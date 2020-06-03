import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { AppRoutingModule } from './app.routing';
import { ComponentsModule } from './components/components.module';

import { AppComponent } from './app.component';

import { BaseLayoutComponent } from './layouts/base-layout.component';
import { LoginComponent } from './login/login.component';
import { SharedImportModule } from './shared-import/shared-import.module';

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
    ComponentsModule,
    SharedImportModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
