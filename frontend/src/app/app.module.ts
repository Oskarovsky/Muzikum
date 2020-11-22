import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import {
    MatButtonModule,
    MatCardModule,
    MatExpansionModule,
    MatInputModule,
    MatListModule,
    MatPaginatorModule, MatSidenavModule,
    MatToolbarModule
} from '@angular/material';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TrackComponent } from './tracks/track/track.component';
import { TrackListComponent } from './tracks/track-list/track-list.component';
import { ProviderComponent } from './providers/provider/provider.component';
import { MatGridListModule} from '@angular/material/grid-list';
import { ProviderListComponent } from './providers/provider-list/provider-list.component';
import { ProviderDetailsComponent } from './providers/provider-details/provider-details.component';
import { AddProviderComponent } from './providers/provider-add/add-provider.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NavigationComponent } from './navigation/navigation.component';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { NotFoundComponent } from './not-found/not-found.component';
import { ToplistComponent } from './playlists/toplist/toplist.component';
import { MatSelectModule } from '@angular/material/select';
import { ToplistEditComponent } from './playlists/toplist-edit/toplist-edit.component';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSortModule } from '@angular/material/sort';
import { PlaylistComponent } from './playlists/playlist/playlist.component';
import { PlaylistAddComponent } from './playlists/playlist-add/playlist-add.component';
import { PlaylistEditComponent } from './playlists/playlist-edit/playlist-edit.component';
import { PlaylistDetailsComponent } from './playlists/playlist-details/playlist-details.component';
import { PlaylistFilterPipe } from './services/playlist/playlist-filter.pipe';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './user/board-admin/board-admin.component';
import { BoardModeratorComponent } from './user/board-moderator/board-moderator.component';
import { BoardUserComponent } from './user/board-user/board-user.component';
import { VideoComponent } from './videos/video/video.component';
import { VideoAddComponent } from './videos/video-add/video-add.component';
import {VideoFilterPipe} from './services/video/video-filter.pipe';
import { VideoDetailsComponent } from './videos/video-details/video-details.component';
import { VideoEditComponent } from './videos/video-edit/video-edit.component';
import { PlaylistAllComponent } from './playlists/playlist-all/playlist-all.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './footer/footer.component';
import { ForgotPasswordComponent } from './user/forgot-password/forgot-password.component';
import { SidebarLeftComponent } from './sidebar/sidebar-left/sidebar-left.component';
import { SidebarRightComponent } from './sidebar/sidebar-right/sidebar-right.component';
import { AddPostComponent } from './article/add-post/add-post.component';
import { PostComponent } from './article/post/post.component';
import { AdminPanelComponent } from './admin/admin-panel/admin-panel.component';
import { ArticlePartComponent } from './admin/components/article-part/article-part.component';
import {CKEditorModule} from 'ng2-ckeditor';
import { PostDetailsComponent } from './article/post-details/post-details.component';
import { UserPartComponent } from './admin/components/user-part/user-part.component';
import { UserFilterPipe } from './services/user/user-filter.pipe';
import { TrackAddComponent } from './tracks/track-add/track-add.component';
import { TokenConfirmationComponent } from './auth/token-confirmation/token-confirmation.component';
import { TrackPartComponent } from './admin/components/track-part/track-part.component';
import { AboutComponent } from './info/about/about.component';
import { ContactComponent } from './info/contact/contact.component';
import { TrackTileComponent } from './tracks/tracks-by-genre/track-tile/track-tile.component';
import { TrackVoteButtonComponent } from './tracks/track-vote-button/track-vote-button.component';
import { TracksPartComponent } from './tracks/tracks-by-genre/tracks-part/tracks-part.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { UserTracksComponent } from './profile/user-tracks/user-tracks.component';
import { UserTracksPartComponent } from './profile/user-tracks-part/user-tracks-part.component';
import { UserTrackTileComponent } from './profile/user-track-tile/user-track-tile.component';
import { TrackCommentAddComponent } from './tracks/track-comment-add/track-comment-add.component';
import { DateAgoPipe } from './pipes/date-ago.pipe';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { TracksVixaComponent } from './tracks/tracks-by-genre/genre/tracks-vixa/tracks-vixa.component';
import { TracksClubComponent } from './tracks/tracks-by-genre/genre/tracks-club/tracks-club.component';
import { TracksDanceComponent } from './tracks/tracks-by-genre/genre/tracks-dance/tracks-dance.component';
import { TracksRetroComponent } from './tracks/tracks-by-genre/genre/tracks-retro/tracks-retro.component';
import { TracksDiscoComponent } from './tracks/tracks-by-genre/genre/tracks-disco/tracks-disco.component';
import { TracksTechnoComponent } from './tracks/tracks-by-genre/genre/tracks-techno/tracks-techno.component';
import { Oauth2RedirectHandlerComponent } from './auth/oauth2-redirect-handler/oauth2-redirect-handler.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import { MenuListItemComponent } from './navigation/menu-list-item/menu-list-item.component';
import { UserEditComponent } from './user/user-edit/user-edit.component';
import { UserChangePasswordComponent } from './user/user-change-password/user-change-password.component';
import { AlertComponent } from './alert/alert.component';
import {AlertModule} from './alert/alert.module';


@NgModule({
  declarations: [
    AppComponent,
    TrackComponent,
    TrackListComponent,
    ProviderComponent,
    ProviderListComponent,
    ProviderDetailsComponent,
    AddProviderComponent,
    NavigationComponent,
    NotFoundComponent,
    ToplistComponent,
    ToplistEditComponent,
    PlaylistComponent,
    PlaylistAddComponent,
    PlaylistEditComponent,
    PlaylistDetailsComponent,
    PlaylistFilterPipe,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    VideoComponent,
    VideoAddComponent,
    VideoFilterPipe,
    VideoDetailsComponent,
    VideoEditComponent,
    PlaylistAllComponent,
    HomeComponent,
    FooterComponent,
    ForgotPasswordComponent,
    SidebarLeftComponent,
    SidebarRightComponent,
    AddPostComponent,
    PostComponent,
    AdminPanelComponent,
    ArticlePartComponent,
    PostDetailsComponent,
    UserPartComponent,
    UserFilterPipe,
    TrackAddComponent,
    TokenConfirmationComponent,
    TrackPartComponent,
    AboutComponent,
    ContactComponent,
    TrackTileComponent,
    TrackVoteButtonComponent,
    TracksPartComponent,
    UserTracksComponent,
    UserTracksPartComponent,
    UserTrackTileComponent,
    TrackCommentAddComponent,
    DateAgoPipe,
    UserProfileComponent,
    TracksVixaComponent,
    TracksClubComponent,
    TracksDanceComponent,
    TracksRetroComponent,
    TracksDiscoComponent,
    TracksTechnoComponent,
    Oauth2RedirectHandlerComponent,
    MenuListItemComponent,
    UserEditComponent,
    UserChangePasswordComponent,
  ],
    imports: [
        AppRoutingModule,
        NoopAnimationsModule,
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatButtonModule,
        MatCardModule,
        MatInputModule,
        MatListModule,
        MatToolbarModule,
        MatGridListModule,
        FormsModule,
        MatMenuModule,
        MatIconModule,
        AngularFontAwesomeModule,
        MatSelectModule,
        ReactiveFormsModule,
        MatTableModule,
        MatTabsModule,
        MatSortModule,
        MatExpansionModule,
        MatPaginatorModule,
        CKEditorModule,
        NgxPaginationModule,
        MatSidenavModule,
        FlexLayoutModule,
        AlertModule
    ],
  exports: [
    TrackComponent,
    TrackListComponent,
    ProviderComponent,
    ProviderListComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
