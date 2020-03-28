import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { TrackComponent } from './track/track.component';
import { ProviderComponent } from './provider/provider.component';
import { TrackListComponent } from './track-list/track-list.component';


const routes: Routes = [
  { path: '', redirectTo: '/', pathMatch: 'full'},
  {
    path: 'tracklist',
    component: TrackListComponent
  },
  {
    path: 'track/:id',
    component: TrackComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
