import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import {IonicModule} from '@ionic/angular';

import { ScanTabPage } from './scan-tab.page';
import {ValideModalComponent} from '../valide-modal/valide-modal.component';
import {DeniedModalComponent} from '../denied-modal/denied-modal.component';

const routes: Routes = [
  {
    path: '',
    component: ScanTabPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [ScanTabPage, ValideModalComponent, DeniedModalComponent],
  entryComponents:[ValideModalComponent, DeniedModalComponent]
})
export class ScanTabPageModule {}
