import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule } from '@angular/material';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TrackComponent } from './track/track.component';
import { TrackListComponent } from './track-list/track-list.component';
import { ProviderComponent } from './provider/provider.component';
import {MatGridListModule} from "@angular/material/grid-list";
import { ProviderListComponent } from './provider-list/provider-list.component';
import { ProviderDetailsComponent } from './provider-details/provider-details.component';
import { AddProviderComponent } from './add-provider/add-provider.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { NavigationComponent } from './navigation/navigation.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { NotFoundComponent } from './not-found/not-found.component';
import { ToplistComponent } from './toplist/toplist.component';
import {MatSelectModule} from "@angular/material/select";
import { ToplistEditComponent } from './toplist-edit/toplist-edit.component';
import {MatTableModule} from "@angular/material/table";
import {MatTabsModule} from "@angular/material/tabs";
import {MatSortModule} from "@angular/material/sort";
import { PlaylistComponent } from './playlist/playlist.component';
import { PlaylistAddComponent } from './playlist-add/playlist-add.component';
import { PlaylistEditComponent } from './playlist-edit/playlist-edit.component';
import { PlaylistDetailsComponent } from './playlist-details/playlist-details.component';
import { PlaylistFilterPipe } from './services/playlist-filter/playlist-filter.pipe';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardUserComponent } from './board-user/board-user.component';


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
    BoardUserComponent
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
