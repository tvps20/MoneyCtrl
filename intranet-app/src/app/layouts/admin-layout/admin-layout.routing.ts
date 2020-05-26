import { Routes } from '@angular/router';

import { DashboardComponent } from '../../dashboard/dashboard.component';
import { UserCompradorComponent } from '../../user-comprador/user-comprador.component';
import { CartaoComponent } from '../../cartao/cartao.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'user-comprador', component: UserCompradorComponent },
    { path: 'cartoes', component: CartaoComponent}
];
