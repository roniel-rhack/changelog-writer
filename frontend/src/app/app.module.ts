import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {HttpClientModule} from '@angular/common/http';
import {ChangelogCreateComponent} from './changelog-create/changelog-create.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {ChangelogDisplayComponent} from './changelog-display/changelog-display.component';
import {MarkdownModule} from 'ngx-markdown';

@NgModule({
  declarations: [
    AppComponent,
    ChangelogCreateComponent,
    ChangelogDisplayComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MarkdownModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
