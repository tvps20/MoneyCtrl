import { Component, OnInit } from '@angular/core';

declare const $: any;
declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}

export const ROUTES: RouteInfo[] = [
  { path: '/dashboard', title: 'Dashboard', icon: 'dashboard', class: '' },
  { path: '/user-comprador', title: 'Compradores', icon: 'person', class: '' },
  { path: '/cartoes', title: 'Cartões', icon: 'credit_card', class: '' },
  { path: '/faturas', title: 'Faturas', icon: 'receipt_long', class: '' },
  { path: '/dividas', title: 'Dividas', icon: 'exposure', class: '' },
  { path: '/contato', title: 'Contato', icon: 'phone_in_talk', class: 'active-pro' },
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  public menuItems: any[];

  constructor() { }

  ngOnInit(): void {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }

  public isMobileMenu() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  }
}
