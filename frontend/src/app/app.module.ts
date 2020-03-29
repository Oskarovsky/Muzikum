import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {BrowserAnimationsModule, NoopAnimationsModule} from '@angular/platform-browser/animations';
import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule } from '@angular/material';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { TrackComponent } from './track/track.component';
import { TrackListComponent } from './track-list/track-list.component';
import { ProviderComponent } from './provider/provider.component';
import {MatGridListModule} from "@angular/material/grid-list";
import { ProviderListComponent } from './provider-list/provider-list.component';
import { ProviderDetailsComponent } from './provider-details/provider-details.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TrackComponent,
    TrackListComponent,
    ProviderComponent,
    ProviderListComponent,
    ProviderDetailsComponent
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
    MatGridListModule
  ],
  exports: [
    TrackComponent,
    TrackListComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
