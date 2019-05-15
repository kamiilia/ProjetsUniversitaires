import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { MenuPage } from './menu.page';

const routes: Routes = [
  {
    path: '',
    component: MenuPage,
    children: [
      {
        path: 'acceuil',
        loadChildren: '../acceuil/acceuil.module#AcceuilPageModule'
      },
      {
        path: 'presence',
        loadChildren: '../presence/presence.module#PresencePageModule'
      },
      {
        path: 'evenements',
        loadChildren: '../evenements/evenements.module#EvenementsPageModule'
      },
      {
        path: 'evenements/event/:titre',
        loadChildren: '../evenement/evenement.module#EvenementPageModule'
      },
      {
      path: 'evenements/new',
      loadChildren: '../ajout-evenement/ajout-evenement.module#AjoutEvenementPageModule'
      }
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [MenuPage]
})
export class MenuPageModule {}
