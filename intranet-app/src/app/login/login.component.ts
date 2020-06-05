import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public formulario: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router) { }

  ngOnInit(): void {
    this.formulario = this.formBuilder.group({
      username: [null, [Validators.required, Validators.minLength(6)]],
      senha: [null, [Validators.required, Validators.minLength(6)]]
    });
  }

  public onSubmit(){
      if(this.formulario.valid){
        // Transformando o obj em Json
        JSON.stringify(this.formulario.value);
        this.router.navigate(['dashboard']);
        // this.formulario.reset();
      }
  }
}
