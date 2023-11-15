import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { SystemComponent } from './system/system.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'system', component: SystemComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProtectedRoutingModule { }
