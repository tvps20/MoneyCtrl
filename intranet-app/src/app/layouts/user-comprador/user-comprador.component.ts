import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-user-comprador',
    templateUrl: './user-comprador.component.html',
    styleUrls: ['./user-comprador.component.css']
})
export class UserCompradorComponent implements OnInit {

    public formulario: FormGroup;

    constructor(private formBuilder: FormBuilder) { }

    ngOnInit(): void {
        this.formulario = this.formBuilder.group({
            nome: [null, [Validators.required, Validators.minLength(6)]],
            sobrenome: [null, [Validators.required, Validators.minLength(6)]],
            perfil: ["COMPRADOR", Validators.required],
            admin: [false],
            username: [null, Validators.minLength(6)],
            email: [null, Validators.email],
            senha: [null, Validators.required],
            confirmarSenha: [null, Validators.required]
          });
          console.log(this.formulario)
    }

    public onSubmit(){
        console.log(this.formulario);
        if(this.formulario.valid){
            // Transformando o obj em Json
            JSON.stringify(this.formulario.value);
            // this.formulario.reset();
        } else {
            this.verificarValidacoesForm(this.formulario);
        }
    }

    private verificarValidacoesForm(formGroup: FormGroup){
        // Retorna uma coleção das propriedades.
        Object.keys(formGroup.controls).forEach(campo => {
            const controle = formGroup.get(campo);
            controle.markAsDirty();
            if(controle instanceof FormGroup){
                this.verificarValidacoesForm(controle);
            }
        })
    }

    public verificarValidField(campo: string): boolean{
        return !this.formulario.get(campo).valid &&
        (this.formulario.get(campo).touched || this.formulario.get(campo).dirty);
    }
}
