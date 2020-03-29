import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { TrackComponent } from './track/track.component';
import { ProviderComponent } from './provider/provider.component';
import { TrackListComponent } from './track-list/track-list.component';
import { ProviderListComponent } from "./provider-list/provider-list.component";
import {ProviderDetailsComponent} from "./provider-details/provider-details.component";


const routes: Routes = [
  {
    path: '',
    redirectTo: '/',
    pathMatch: 'full'
  },
  {
    path: 'tracklist',
    component: TrackListComponent
  },
  {
    path: 'track/:id',
    component: TrackComponent
  },
  {
    path: 'provider/:id_provider',
    component: ProviderDetailsComponent
  },
  {
    path: 'provider',
    component: ProviderListComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
