import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
    selector: 'app-fatura-detail',
    templateUrl: './fatura-detail.component.html',
    styleUrls: ['./fatura-detail.component.css'],
})
export class FaturaDetailComponent implements OnInit {

    public disableSelect = new FormControl(false);
    public datemask = [/\d/, /\d/, '/', /\d/, /\d/];
    public valormask = [/\d/, /\d/, ',', /\d/, /\d/, ' R$'];
    public panelOpenState = false;
    public step = 0;

    constructor() { }

    ngOnInit(): void { }

    setStep(index: number) {
        this.step = index;
    }

    nextStep() {
        this.step++;
    }

    prevStep() {
        this.step--;
    }
}
