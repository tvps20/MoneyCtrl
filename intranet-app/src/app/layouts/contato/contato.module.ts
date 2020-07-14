import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { ContatoComponent } from './contato.component';

import { SharedImportModule } from 'src/app/shared/imports/shared-import.module';
import { ComponentsModule } from 'src/app/shared/components/components.module';


@NgModule({
    declarations: [
        ContatoComponent
    ],
    imports: [
        CommonModule,
        RouterModule,
        SharedImportModule,
        ComponentsModule
    ],
    exports: [
        ContatoComponent
    ]
})
export class ContatoModule { }
