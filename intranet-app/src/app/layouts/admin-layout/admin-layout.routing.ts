import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserCompradorComponent } from '../../user-comprador/user-comprador.component';
import { CartaoComponent } from '../../cartao/cartao.component';
import { CartaoDetailComponent } from '../../cartao/cartao-detail/cartao-detail.component';
import { FaturaDetailComponent } from '../../cartao/cartao-detail/fatura-detail/fatura-detail.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'user-comprador', component: UserCompradorComponent },
    {
        path: 'cartoes',
        children: [
            { path: '', component: CartaoComponent },
            {
                path: ':id',
                children: [
                    { path: '', component: CartaoDetailComponent },
                    { path: 'faturas/:id', component: FaturaDetailComponent }
                ]
            }
        ]
    }
];
