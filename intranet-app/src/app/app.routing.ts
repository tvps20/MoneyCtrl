import { NgModule } from '@angular/core';
import { CommonModule, } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { Routes, RouterModule } from '@angular/router';

import { BaseLayoutComponent } from './layouts/base-layout/base-layout.component';

const ROUTES: Routes = [
    { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
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