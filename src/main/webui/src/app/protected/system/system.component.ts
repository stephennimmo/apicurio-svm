import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { System, SystemService } from '../../core/service/system/system.service';
import { BaseComponent } from '../../shared/base-component';

@Component({
  selector: 'app-system',
  templateUrl: './system.component.html',
  styleUrls: ['./system.component.scss']
})
export class SystemComponent extends BaseComponent {

  systems$?: Observable<System[]>;

  constructor(private readonly systemService: SystemService) {
    super();
  }

  init(): void {
    this.systems$ = this.systemService.findAll();
  }

  destroy(): void {
  }





}
