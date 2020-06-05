import { FormValidations } from 'src/app/shared/util/form-validations';
import { FormControl } from '@angular/forms';
import { Component, OnInit, Input } from '@angular/core';
import { ValidFormsService } from '../../services/valid-forms.service';

@Component({
    selector: 'app-erro-msg',
    templateUrl: './erro-msg.component.html',
    styleUrls: ['./erro-msg.component.css']
})
export class ErroMsgComponent implements OnInit {

    //@Input() msgErro: string;
    //@Input() mostrarErro: string;
    @Input() control: FormControl;
    @Input() label: string;

    constructor(private validFormsService: ValidFormsService) { }

    ngOnInit(): void {
    }

    get errorMessage(){
        for(let propertyName in this.control.errors){
            if(this.control.errors.hasOwnProperty(propertyName) &&
            this.control.touched) {
                return FormValidations.getErrorMsg(this.label, propertyName, this.control.errors[propertyName]);
            }
        }
        return null;
    }

    public verificarValidField(): boolean {
        return !this.control.valid &&
            (this.control.touched || this.control.dirty);
    }
}
