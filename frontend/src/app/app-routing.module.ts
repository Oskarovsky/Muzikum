import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TrackComponent} from './track/track.component';
import {TrackListComponent} from './track-list/track-list.component';
import {ProviderListComponent} from './provider-list/provider-list.component';
import {ProviderComponent} from './provider/provider.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {ToplistComponent} from './toplist/toplist.component';
import {ToplistEditComponent} from './toplist-edit/toplist-edit.component';
import {ProviderDetailsComponent} from './provider-details/provider-details.component';
import {PlaylistComponent} from './playlist/playlist.component';
import {PlaylistAddComponent} from './playlist-add/playlist-add.component';
import {PlaylistEditComponent} from './playlist-edit/playlist-edit.component';
import {PlaylistDetailsComponent} from './playlist-details/playlist-details.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {BoardModeratorComponent} from './board-moderator/board-moderator.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardUserComponent} from './board-user/board-user.component';
import {VideoComponent} from './video/video.component';
import {VideoAddComponent} from './video-add/video-add.component';
import {VideoDetailsComponent} from './video-details/video-details.component';
import {VideoEditComponent} from './video-edit/video-edit.component';
import {PlaylistAllComponent} from './playlist-all/playlist-all.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';


const routes: Routes = [
  { path: 'tracklist', component: TrackListComponent },
  { path: '', component: HomeComponent },
  { path: 'track/:id', component: TrackComponent },
  { path: 'provider/:id/tracks', component: TrackListComponent },
  { path: 'provider', component: ProviderListComponent },
  { path: 'provider/:id/details', component: ProviderDetailsComponent },
  { path: 'provider/:providerName/all-tracks', component: TrackListComponent },
  { path: 'provider/:id/tracks/:genre', component: TrackListComponent },
  { path: 'tracklist/:genre', component: TrackListComponent },
  { path: 'toplist/:genre', component: ToplistComponent },
  { path: 'playlist/all', component: PlaylistAllComponent },
  { path: 'playlist/all/:username', component: PlaylistComponent },
  { path: 'toplist/:genre/edit', component: ToplistEditComponent },
  { path: 'playlist/add', component: PlaylistAddComponent },
  { path: 'playlist/:id/edit', component: PlaylistEditComponent },
  { path: 'playlist/:id/details', component: PlaylistDetailsComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'video/category/:category', component: VideoComponent },
  { path: 'video/:id/edit', component: VideoEditComponent },
  { path: 'video/:id/details', component: VideoDetailsComponent },
  { path: 'video/add', component: VideoAddComponent },
  { path: '', redirectTo: '', pathMatch: 'full' },
  { path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
