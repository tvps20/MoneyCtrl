import { FormControl, FormGroup } from '@angular/forms';
import { ValidFormsService } from '../services/valid-forms.service';
import { map } from 'rxjs/operators';


export class FormValidations {

    constructor(){}

    static equalsTo(olderField: string){
        const validator = (formControl: FormControl) => {
            if(olderField == null){
                throw new Error('É necessário informar um campo.');
            }

            if(!formControl.root || !(<FormGroup>formControl.root).controls){
                return null;
            }

            const field = (<FormGroup>formControl.root).get(olderField);

            if(!field){
                throw new Error('É necessario informar um campo válido');
            }
            if(field.value !== formControl.value){
                return { equalsTo: false }
            }

            return null;
        }
        return validator;
    }

    static getErrorMsg(fieldName: string, validatorName: string, validatorValue?: any){
        const config = {
            'required': `${fieldName} é origatório.`,
            'minlength': `${fieldName} precisa ter no mínimo ${validatorValue.requiredLength} caracteres.`,
            'maxlength': `${fieldName} precisa ter no máximo ${validatorValue.requiredLength} caracteres.`,
            'email': `${fieldName} é inválido.`,
            'emailInvalido': 'Email já cadastrado.',
            'equalsTo': `As senhas são diferentes.`
        }

        return config[validatorName];
    }
}
