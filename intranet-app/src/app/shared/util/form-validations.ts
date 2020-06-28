import { FormControl, FormGroup, FormArray } from '@angular/forms';

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

    static onlyLetters(control: FormControl){
        const valor: string = control.value;
        if(valor && valor !== ''){
            const regex = new RegExp("^[aA-zZ ]+((\s[aA-zZ]+)+)?$");
            return regex.test(valor) ? null : { OnlyLetters: false };
        }

        return null;
    }

    static notStartNumber(control: FormControl){
        const valor: string = control.value;
        if(valor && valor !== ''){
            const regex = new RegExp("^[^0-9]");
            return regex.test(valor) ? null : { startNumber: true };
        }

        return null;
    }

    static validaArray(olderArrayField: string){
        const validator = (formControl: FormControl) => {
            if(olderArrayField == null){
                throw new Error('É necessário informar um campo.');
            }

            if(!formControl.root || !(<FormGroup>formControl.root).controls){
                return null;
            }

            const field = (<FormArray>formControl.root).get(olderArrayField);

            if(field.value.length <= 0){
                return { isEmpty: true }
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
            'valorUnique': `${fieldName} já cadastrado.`,
            'equalsTo': `As senhas são diferentes.`,
            'pattern': `${fieldName} não atende ao regex.`,
            'OnlyLetters': `${fieldName} deve conter apenas letras.`,
            'startNumber': `${fieldName} não pode começar com números.`,
            'isEmpty': `Precisa ter um ou mais ${fieldName}.`
        }

        return config[validatorName];
    }
}
