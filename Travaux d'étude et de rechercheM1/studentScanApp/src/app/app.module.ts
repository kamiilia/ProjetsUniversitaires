import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { NavController } from '@ionic/angular';

import { NFC } from '@ionic-native/nfc/ngx';
import { Platform } from '@ionic/angular';
import { SQLite } from '@ionic-native/sqlite/ngx';
import {File} from '@ionic-native/file/ngx';
import { FilePath } from '@ionic-native/file-path/ngx';
import { EmailComposer } from '@ionic-native/email-composer/ngx';
import { AlertController } from '@ionic/angular';
import { LocalNotifications } from '@ionic-native/local-notifications/ngx';
@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [BrowserModule, IonicModule.forRoot(), AppRoutingModule],
  providers: [
      StatusBar,
      SplashScreen,
      NFC,
      SQLite,
      LocalNotifications,
      File,
      NavController,
      EmailComposer,
      FilePath,
      AlertController,
      Platform,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
