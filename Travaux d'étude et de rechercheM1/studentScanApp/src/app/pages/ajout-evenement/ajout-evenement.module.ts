import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { AjoutEvenementPage } from './ajout-evenement.page';

const routes: Routes = [
  {
    path: '',
    component: AjoutEvenementPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [AjoutEvenementPage]
})
export class AjoutEvenementPageModule {}
