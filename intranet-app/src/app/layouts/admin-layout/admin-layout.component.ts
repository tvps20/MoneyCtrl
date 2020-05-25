import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Location, LocationStrategy, PathLocationStrategy, PopStateEvent } from '@angular/common';
import { Router, NavigationEnd, NavigationStart } from '@angular/router';
import * as $ from "jquery";

@Component({
  selector: 'app-admin-layout',
  templateUrl: './admin-layout.component.html',
  styleUrls: ['./admin-layout.component.css']
})
export class AdminLayoutComponent implements OnInit {

  private lastPoppedUrl: string;
  private yScrollStack: number[] = [];

  constructor(public location: Location, private router: Router) { }

  ngOnInit(): void {
    
  }

  public isMaps(path) {
    return true;
  }

  public isMac(): boolean {
    return false;
  }
}
