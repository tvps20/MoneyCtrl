import { ValidFormsService } from './../../services/valid-forms.service';
import { Component, OnInit } from '@angular/core';
import { BaseFormComponent } from '../base-form/base-form.component';

@Component({
    selector: 'app-base-form-list',
    template: '<div></div>'
})
export abstract class BaseFormListComponent extends BaseFormComponent implements OnInit {

    constructor(protected validFormsService: ValidFormsService) {
        super(validFormsService);
    }

    ngOnInit(): void {
    }

    public abstract onSelectedEntity(entity: any);

    public abstract onDelete(event: any);

    public paginate(array: any[], page_size, page_index) {
        // human-readable page numbers usually start with 1, so we reduce 1 in the first argument
        return array.slice((page_index - 1) * page_size, page_index * page_size);
    }
}
