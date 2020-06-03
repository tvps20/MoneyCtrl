import { VerificaUniqueFieldService } from './../services/verifica-unique-field.service';
import { FormControl, FormGroup } from '@angular/forms';
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
}
