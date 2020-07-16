import { FormControl, ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { Component, Input, forwardRef } from '@angular/core';

import { ValidFormsService } from '../../services/valid-forms.service';

const INPUT_FIELD_VALUE_ACESSOR: any = {
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => InputFieldComponent),
    multi: true
};

@Component({
    selector: 'app-input-field',
    templateUrl: './input-field.component.html',
    styleUrls: ['./input-field.component.css'],
    providers: [INPUT_FIELD_VALUE_ACESSOR]
})
export class InputFieldComponent implements ControlValueAccessor {

    @Input() label: string;
    @Input() tipo: string = 'text';
    @Input() control: FormControl;
    @Input() isReadOnly = false;
    @Input() submitted = false;
    @Input() uniqueFiled= false;
    @Input() maxLengthInput = 256;
    @Input() showMaxLength = true;

    private innerValue: any;

    get value() {
        return this.innerValue;
    }

    set value(valor: any) {
        if (valor !== this.innerValue) {
            this.innerValue = valor;
            this.onChangeCb(valor);
        }
    }

    constructor(private validFormsService: ValidFormsService) { }

    onChangeCb: (_: any) => void = () => { };
    onTouchedCb: (_: any) => void = () => { };

    writeValue(valor: any): void {
        // Chamando a função de set
        this.value = valor;
    }
    registerOnChange(fn: any): void {
        this.onChangeCb = fn;
    }
    registerOnTouched(fn: any): void {
        this.onTouchedCb = fn;
    }
    setDisabledState?(isDisabled: boolean): void {
        this.isReadOnly = isDisabled;
    }

    public verificarValidControl(formControl: FormControl): boolean {
        return this.validFormsService.verificarValidField(formControl);
    }

    public errorMessage() {
        return this.validFormsService.errorMessage(this.control, this.label);
    }

    public markAsTouched() {
        this.control.markAsTouched();
    }

    public aplicaClassErro() {
        return {
            'mat-form-field-invalid': this.validFormsService.verificarValidField(this.control)
        };
    }
}
