import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { BaseLayoutComponent } from './layouts/base-layout/base-layout.component';

const ROUTES: Routes = [
    { path: '', redirectTo: 'auth', pathMatch: 'full' },
    {
        path: 'auth',
        loadChildren: () => import('./login/login.module').then(m => m.LoginModule)
    },
    {
        path: '',
        component: BaseLayoutComponent,
        children: [{
            path: '',
            loadChildren: () => import('./layouts/base-layout/base-layout.module').then(m => m.BaseLayoutModule)
        }]
    }
];

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        RouterModule.forRoot(ROUTES)
    ],
    exports: [
    ],
})
export class AppRoutingModule { }