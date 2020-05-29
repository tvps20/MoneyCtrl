import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { UserCompradorComponent } from './user-comprador.component';
import { SharedImportModule } from 'src/app/shared-import/shared-import.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatRippleModule, MatNativeDateModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { TextMaskModule } from 'angular2-text-mask';
import { MatExpansionModule } from '@angular/material/expansion';



@NgModule({
  declarations: [
    UserCompradorComponent
  ],
  imports: [
    CommonModule,
    SharedImportModule
  ],
  exports: [
    UserCompradorComponent
  ]
})
export class UserCompradorModule { }
