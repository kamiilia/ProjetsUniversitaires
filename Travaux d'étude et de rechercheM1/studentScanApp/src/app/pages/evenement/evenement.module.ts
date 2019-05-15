import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { EvenementPage } from './evenement.page';

const routes: Routes = [
    {
        path: 'tabs',
        component: EvenementPage,
        children: [
            {
                path: 'statstab',
                loadChildren: '../stats-tab/stats-tab.module#StatsTabPageModule'
            },
            {
                path: 'scantab',
                loadChildren: '../scan-tab/scan-tab.module#ScanTabPageModule'
            },
            /*{
                path: 'mailtab',
                loadChildren: '../mail-tab/mail-tab.module#MailTabPageModule'
            }*/
        ]
    },
    {
        path: '',
        redirectTo: 'tabs/scantab',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RouterModule.forChild(routes)
    ],
    declarations: [EvenementPage]
})
export class EvenementPageModule {}
