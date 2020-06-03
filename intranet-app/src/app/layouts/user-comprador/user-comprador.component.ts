import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { ValidFormsService } from 'src/app/shared/services/valid-forms.service';

@Component({
    selector: 'app-user-comprador',
    templateUrl: './user-comprador.component.html',
    styleUrls: ['./user-comprador.component.css']
})
export class UserCompradorComponent implements OnInit {

    public formulario: FormGroup;

    constructor(private formBuilder: FormBuilder,
        private validFormsService: ValidFormsService) { }

    ngOnInit(): void {
        this.formulario = this.createForm();
    }

    public onSubmit(): void {
        if (this.formulario.valid) {
            let valueSubmit = this.addRoles(this.formulario);
            console.log(valueSubmit);
            // Transformando o obj em Json
            JSON.stringify(valueSubmit);
            // this.formulario.reset();
        } else {
            this.validFormsService.verificarValidacoesForm(this.formulario);
        }
    }

    public verificarValidField(campo: string): boolean {
        return this.validFormsService.verificarValidField(campo, this.formulario);
    }

    private createForm():FormGroup {
        return this.formBuilder.group({
            nome: [null, [Validators.required, Validators.minLength(6)]],
            sobrenome: [null, [Validators.required, Validators.minLength(6)]],
            perfil: ["COMPRADOR", Validators.required],
            admin: [false],
            username: [null, Validators.minLength(6)],
            email: [null, Validators.email],
            senha: [null, Validators.required],
            confirmarSenha: [null, Validators.required],
            roles: [[]]
        });
    }

    private addRoles(form: FormGroup): any {
        let valueSubmit = Object.assign({}, form.value);
        if(form.get('admin').value){
            valueSubmit.roles = ["USER", "ADMIN"];
        } else {
            valueSubmit.roles = ["USER"];
        }

        return valueSubmit;
    }
}
