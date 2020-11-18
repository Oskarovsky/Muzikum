import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TrackComponent} from './tracks/track/track.component';
import {TrackListComponent} from './tracks/track-list/track-list.component';
import {ProviderListComponent} from './providers/provider-list/provider-list.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {ToplistComponent} from './playlists/toplist/toplist.component';
import {ToplistEditComponent} from './playlists/toplist-edit/toplist-edit.component';
import {ProviderDetailsComponent} from './providers/provider-details/provider-details.component';
import {PlaylistComponent} from './playlists/playlist/playlist.component';
import {PlaylistAddComponent} from './playlists/playlist-add/playlist-add.component';
import {PlaylistEditComponent} from './playlists/playlist-edit/playlist-edit.component';
import {PlaylistDetailsComponent} from './playlists/playlist-details/playlist-details.component';
import {LoginComponent} from './auth/login/login.component';
import {RegisterComponent} from './auth/register/register.component';
import {BoardModeratorComponent} from './user/board-moderator/board-moderator.component';
import {BoardAdminComponent} from './user/board-admin/board-admin.component';
import {BoardUserComponent} from './user/board-user/board-user.component';
import {VideoComponent} from './videos/video/video.component';
import {VideoAddComponent} from './videos/video-add/video-add.component';
import {VideoDetailsComponent} from './videos/video-details/video-details.component';
import {VideoEditComponent} from './videos/video-edit/video-edit.component';
import {PlaylistAllComponent} from './playlists/playlist-all/playlist-all.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {PostComponent} from './article/post/post.component';
import {AddPostComponent} from './article/add-post/add-post.component';
import {AdminPanelComponent} from './admin/admin-panel/admin-panel.component';
import {PostDetailsComponent} from './article/post-details/post-details.component';
import {TrackAddComponent} from './tracks/track-add/track-add.component';
import {TokenConfirmationComponent} from './auth/token-confirmation/token-confirmation.component';
import {ContactComponent} from './info/contact/contact.component';
import {AboutComponent} from './info/about/about.component';
import {TracksPartComponent} from './tracks/tracks-by-genre/tracks-part/tracks-part.component';
import {UserTracksComponent} from './profile/user-tracks/user-tracks.component';
import {UserTracksPartComponent} from './profile/user-tracks-part/user-tracks-part.component';
import {UserProfileComponent} from './user/user-profile/user-profile.component';
import {TracksVixaComponent} from './tracks/tracks-by-genre/genre/tracks-vixa/tracks-vixa.component';
import {TracksClubComponent} from './tracks/tracks-by-genre/genre/tracks-club/tracks-club.component';
import {TracksDiscoComponent} from './tracks/tracks-by-genre/genre/tracks-disco/tracks-disco.component';
import {TracksTechnoComponent} from './tracks/tracks-by-genre/genre/tracks-techno/tracks-techno.component';
import {TracksRetroComponent} from './tracks/tracks-by-genre/genre/tracks-retro/tracks-retro.component';
import {TracksDanceComponent} from './tracks/tracks-by-genre/genre/tracks-dance/tracks-dance.component';
import {Oauth2RedirectHandlerComponent} from './auth/oauth2-redirect-handler/oauth2-redirect-handler.component';
import {UserEditComponent} from './user/user-edit/user-edit.component';
import {ForgotPasswordComponent} from './user/forgot-password/forgot-password.component';
import {UserChangePasswordComponent} from './user/user-change-password/user-change-password.component';


const routes: Routes = [
  { path: 'track/:id', component: TrackComponent },
  { path: 'tracks/add', component: TrackAddComponent },

  /* TRACKS */
  { path: 'tracklist', component: TrackListComponent },
  { path: 'tracklist/:genre', component: TrackListComponent },
  // { path: 'tracks/:genre', component: TracksPartComponent },
  // { path: 'tracks/"genre"/:page', component: TracksClubComponent },

  { path: 'tracks/vixa', component: TracksVixaComponent },
  { path: 'tracks/club', component: TracksClubComponent },
  { path: 'tracks/dance', component: TracksDanceComponent },
  { path: 'tracks/retro', component: TracksRetroComponent },
  { path: 'tracks/techno', component: TracksTechnoComponent },
  { path: 'tracks/disco', component: TracksDiscoComponent },

  { path: 'tracks/vixa/:page', component: TracksVixaComponent },
  { path: 'tracks/club/:page', component: TracksClubComponent },
  { path: 'tracks/dance/:page', component: TracksDanceComponent },
  { path: 'tracks/retro/:page', component: TracksRetroComponent },
  { path: 'tracks/techno/:page', component: TracksTechnoComponent },
  { path: 'tracks/disco/:page', component: TracksDiscoComponent },

  { path: 'provider/:id/tracks', component: TrackListComponent },
  { path: 'provider', component: ProviderListComponent },
  { path: 'provider/:id/details', component: ProviderDetailsComponent },
  { path: 'provider/:providerName/all-tracks', component: TrackListComponent },
  { path: 'provider/:id/tracks/:genre', component: TrackListComponent },
  { path: 'toplist/:genre', component: ToplistComponent },
  { path: 'playlist/all', component: PlaylistAllComponent },
  { path: 'playlist/all/:username', component: PlaylistComponent },
  { path: 'toplist/:genre/edit', component: ToplistEditComponent },
  { path: 'playlist/add', component: PlaylistAddComponent },
  { path: 'playlist/:id/edit', component: PlaylistEditComponent },
  { path: 'playlist/:id/details', component: PlaylistDetailsComponent },
  { path: 'post/user/:username', component: PostComponent},
  { path: 'post/add', component: AddPostComponent},
  { path: 'post/:id/details', component: PostDetailsComponent},
  { path: 'video/category/:category', component: VideoComponent },
  { path: 'video/:id/edit', component: VideoEditComponent },
  { path: 'video/:id/details', component: VideoDetailsComponent },
  { path: 'video/add', component: VideoAddComponent },

  /* INFO */
  { path: 'info/about', component: AboutComponent },
  { path: 'info/contact', component: ContactComponent },

  /* USER */
  { path: 'login', component: LoginComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'profile/edit', component: UserEditComponent },
  { path: 'profile/edit/changePassword', component: UserChangePasswordComponent },
  { path: 'profile/tracks/:page', component: UserTracksPartComponent },
  { path: 'profile/tracks', component: UserTracksPartComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'profile/:username', component: UserProfileComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'confirmAccount/:token', component: TokenConfirmationComponent },
  { path: 'oauth2/redirect', component: Oauth2RedirectHandlerComponent },

  /* ADMIN */
  { path: 'admin', component: BoardAdminComponent },
  { path: 'admin/panel', component: AdminPanelComponent },

  /* GLOBAL */
  { path: '', component: HomeComponent },
  { path: '', redirectTo: '', pathMatch: 'full' },
  { path: '**', component: NotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
