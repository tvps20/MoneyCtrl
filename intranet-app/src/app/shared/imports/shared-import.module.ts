import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { NgxMaskModule, IConfig } from 'ngx-mask';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatRadioModule } from '@angular/material/radio';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule, MatPaginatorIntl } from '@angular/material/paginator';
import { MatPaginatorIntlCro } from '../util/MatPaginatorIntlCustom';

const maskConfigFunction: () => Partial<IConfig> = () => {
    return {
        validation: false,
    };
};

@NgModule({
    declarations: [],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatRippleModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatSelectModule,
        MatTooltipModule,
        MatNativeDateModule,
        MatDatepickerModule,
        MatCheckboxModule,
        NgxMaskModule.forRoot(maskConfigFunction),
        MatExpansionModule,
        MatRadioModule,
        MatPaginatorModule
    ],
    exports: [
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatRippleModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatSelectModule,
        MatTooltipModule,
        MatNativeDateModule,
        MatDatepickerModule,
        MatCheckboxModule,
        NgxMaskModule,
        MatExpansionModule,
        MatRadioModule,
        MatPaginatorModule
    ],
    providers: [
        { provide: MatPaginatorIntl, useClass: MatPaginatorIntlCro }
    ]
})
export class SharedImportModule { }
