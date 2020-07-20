import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

import { FormValidations } from './../shared/util/form-validations';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';
import { ValidFormsService } from './../shared/services/valid-forms.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent extends BaseFormComponent implements OnInit {

    constructor(protected validFormsService: ValidFormsService,
        private formBuilder: FormBuilder,
        private router: Router) {
        super(validFormsService);
    }

    ngOnInit(): void {
        this.formulario = this.createForm();
    }

    public submit() {
        this.router.navigate(['dashboard']);
    }

    public createEntity(entity: any, msgSuccess: string, msgError: string) {
        throw new Error("Method not implemented.");
    }

    public createForm() {
        return this.formBuilder.group({
            login: [null, [FormValidations.notStartNumber, Validators.required]],
            password: [null, [Validators.required]]
        });
    }

    public defaultValuesForms() {
        throw new Error("Method not implemented.");
    }
}
