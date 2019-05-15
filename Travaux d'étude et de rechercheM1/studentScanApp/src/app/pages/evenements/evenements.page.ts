import { Component, OnInit } from '@angular/core';
import { Event } from '../../models/event';
import {EvenementsService} from '../../services/evenements.service';
import {AlertController, Platform} from '@ionic/angular';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-evenements',
  templateUrl: './evenements.page.html',
  styleUrls: ['./evenements.page.scss'],
})
export class EvenementsPage implements OnInit {

  listEvents: Event[];

  constructor(private eventsService: EvenementsService, private platform: Platform, private navCtrl: NavController, private alertController: AlertController) {
    this.listEvents = [];
    this.platform.ready().then(() => {
      this.eventsService.createDatabaseFile().then((db) => {
        this.eventsService.getEvents().then((data: any) => { //get Events from db
          this.listEvents = data;
        });
      });
    }).catch((e) => {alert('Erreur lors de la création de la base de donnée'); });
  }

  async suppEvent(event: Event) { //Alert when delete Event
    const alert = await this.alertController.create({
      header: 'Supprimer l\'événement ' + event.name ,
      buttons: [
        {
          text: 'Non',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
            console.log('Confirm Cancel');
          }
        },
        {
          text: 'Oui',
          handler: () => {
            console.log(event.id);
            this.eventsService.suppEvent(event.id);
            this.eventsService.getEvents().then((data: any) => {
              this.listEvents = data;
            });
          }
        }
      ]
    });
    await alert.present();
  }

  public suppAll() { // Delete all events from db
    this.eventsService.viderliste();
    this.eventsService.getEvents().then((data: any) => {
      this.listEvents = data;
    });
  }
 ngOnInit() {}

 public refresh() { //reload Events
   this.listEvents = [];
   this.platform.ready().then(() => {
     this.eventsService.createDatabaseFile().then((db) => {
       this.eventsService.getEvents().then((data: any) => {
         this.listEvents = data;
       });
     });
   }).catch((e) => {alert('Erreur lors de la création de la base de donnée'); });
 }

  ionViewWillEnter() {
    this.refresh();
  }

}
