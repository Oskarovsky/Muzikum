import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TrackComponent} from './track/track.component';
import {TrackListComponent} from './track-list/track-list.component';
import {ProviderListComponent} from "./provider-list/provider-list.component";
import {ProviderComponent} from "./provider/provider.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {ToplistComponent} from "./toplist/toplist.component";
import {ToplistEditComponent} from "./toplist-edit/toplist-edit.component";
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
    path: 'provider/:id/tracks',
    component: TrackListComponent
  },
  {
    path: 'provider',
    component: ProviderListComponent
  },
  {
    path: 'provider/:id/details',
    component: ProviderDetailsComponent
  },
  {
    path: 'provider/:providerName/all-tracks',
    component: TrackListComponent
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
    path: 'toplist/:genre',
    component: ToplistComponent
  },
  {
    path: 'toplist/:genre/edit',
    component: ToplistEditComponent
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing:true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
