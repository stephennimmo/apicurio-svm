import { Component, OnDestroy, OnInit } from "@angular/core";
import { Subscription } from 'rxjs';

@Component({ template: '' })
export abstract class BaseComponent implements OnInit, OnDestroy {

  errors?: Error[];
  subscriptions?: Subscription[] = [];

  abstract init(): void;

  abstract destroy(): void;

  ngOnInit(): void {
    this.init();
  }

  ngOnDestroy(): void {
    this.destroy();
  }

}
