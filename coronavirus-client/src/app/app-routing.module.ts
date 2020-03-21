import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewdataComponent } from './viewdata/viewdata.component';

const routes: Routes = [
  {path:"",component:ViewdataComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
