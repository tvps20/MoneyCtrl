import { Routes } from '@angular/router';

import { DashboardComponent } from './dashboard/dashboard.component';
import { CartaoComponent } from './cartao/cartao.component';
import { CartaoDetailComponent } from './cartao/cartao-detail/cartao-detail.component';
import { FaturaComponent } from './cartao/fatura/fatura.component';
import { FaturaDetailComponent } from './cartao/fatura/fatura-detail/fatura-detail.component';
import { DividaComponent } from './divida/divida.component';
import { UserCompradorComponent } from './user-comprador/user-comprador.component';
import { CompradorDetailComponent } from './user-comprador/comprador-detail/comprador-detail.component';

import { CompradorResolverGuard } from './guards/comprador-resolver.guard';
import { FaturaResolveGuard } from './guards/fatura-resolve.guard';
import { CartaoResolveGuard } from './guards/cartao-resolve.guard';


export const BaseLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    {
        path: 'user-comprador',
        children: [
            { path: '', component: UserCompradorComponent },
            { path: ':id', component: CompradorDetailComponent, resolve: { comprador: CompradorResolverGuard } }
        ]
    },
    {
        path: 'cartoes',
        children: [
            { path: '', component: CartaoComponent },
            { path: ':id', component: CartaoDetailComponent, resolve: { cartao: CartaoResolveGuard } }
        ]
    },
    {
        path: 'faturas',
        children: [
            { path: '', component: FaturaComponent },
            { path: ':id', component: FaturaDetailComponent, resolve: { fatura: FaturaResolveGuard } }
        ]
    },
    { path: 'dividas', component: DividaComponent }
];
