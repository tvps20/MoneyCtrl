import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserCompradorComponent } from '../../user-comprador/user-comprador.component';
import { CartaoComponent } from '../../cartao/cartao.component';
import { CartaoDetailComponent } from '../../cartao/cartao-detail/cartao-detail.component';
import { FaturaComponent } from '../../cartao/fatura/fatura.component';
import { FaturaDetailComponent } from '../../cartao/fatura/fatura-detail/fatura-detail.component';
import { DividaComponent } from '../../divida/divida.component';

export const BaseLayoutRoutes: Routes = [
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
    },
    { path: 'faturas', component: FaturaComponent },
    { path: 'dividas', component: DividaComponent }
];
