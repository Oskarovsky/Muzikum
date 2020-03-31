import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TrackComponent} from './track/track.component';
import {TrackListComponent} from './track-list/track-list.component';
import {ProviderListComponent} from "./provider-list/provider-list.component";
import {ProviderComponent} from "./provider/provider.component";
import {NotFoundComponent} from "./not-found/not-found.component";


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
    path: 'provider/:id/tracks',
    component: TrackListComponent
  },
  {
    path: 'provider',
    component: ProviderListComponent
  },
  {
    path: 'provider/:id/details',
    component: ProviderComponent
  },
  {
    path: 'provider/:id/tracks/:genre',
    component: TrackListComponent
  },
  {
    path: 'tracklist/:genre',
    component: TrackListComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
