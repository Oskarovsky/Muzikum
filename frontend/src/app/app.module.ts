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
    ToplistEditComponent
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
