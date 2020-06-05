
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { map } from 'rxjs/operators';

import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';
import { BaseFormComponent } from 'src/app/shared/components/base-form/base-form.component';
import { FormValidations } from 'src/app/shared/util/form-validations';

@Component({
    selector: 'app-user-comprador',
    templateUrl: './user-comprador.component.html',
    styleUrls: ['./user-comprador.component.css']
})
export class UserCompradorComponent extends BaseFormComponent implements OnInit {

    constructor(private formBuilder: FormBuilder,
        public validFormsService: ValidFormsService) { super(validFormsService); }

    ngOnInit(): void {
        this.formulario = this.createForm();
    }

    public submit() {
        let valueSubmit = this.addRoles(this.formulario);
        console.log(valueSubmit);
        // Transformando o obj em Json
        JSON.stringify(valueSubmit);
    }

    private createForm(): FormGroup {
        return this.formBuilder.group({
            nome: [null, [Validators.required, Validators.minLength(6)]],
            sobrenome: [null, [Validators.required, Validators.minLength(6)]],
            perfil: ["COMPRADOR", Validators.required],
            admin: [false],
            username: [null, Validators.minLength(6)],
            email: [null, Validators.email, [this.validarEmail.bind(this)]],
            senha: [null, [Validators.required]],
            confirmarSenha: [null, [FormValidations.equalsTo('senha'), Validators.required]],
            roles: [[]]
        });
    }

    private addRoles(form: FormGroup): any {
        let valueSubmit = Object.assign({}, form.value);
        if (form.get('admin').value) {
            valueSubmit.roles = ["USER", "ADMIN"];
        } else {
            valueSubmit.roles = ["USER"];
        }

        return valueSubmit;
    }

    private validarEmail(formControl: FormControl) {
        return this.validFormsService.verificaEmail(formControl.value)
            .pipe(map(emailExist => emailExist ? { emailInvalido: true } : null));
    }
}
