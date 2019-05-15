import { Component, OnInit } from '@angular/core';
import { Event } from '../../models/event';
import {EvenementsService} from '../../services/evenements.service';
import {Platform} from '@ionic/angular';
import {Router} from '@angular/router';
import {EvenementsPage} from '../evenements/evenements.page';

@Component({
  selector: 'app-ajout-evenement',
  templateUrl: './ajout-evenement.page.html',
  styleUrls: ['./ajout-evenement.page.scss'],
})
export class AjoutEvenementPage implements OnInit {

  event: Event;
  evenements: EvenementsPage;

  constructor(private eventsService: EvenementsService, private platform: Platform, private router: Router) {
    this.event = new Event();
    this.platform.ready().then(() => {
      this.eventsService.createDatabaseFile().then((db) => {
      });
    }).catch((e) => {alert('Erreur lors de la création de la base de donnée'); });
  }

  ngOnInit() {
  }

  addEvent() {
    const tmp = new Event();
    tmp.name = this.event.name;
    tmp.description = this.event.description;
    tmp.dateDebut = new Date(this.event.dateDebut).toLocaleDateString();
    tmp.dateFin = new Date(this.event.dateFin).toLocaleDateString();
    tmp.heureDebut = new Date(this.event.heureDebut).toLocaleTimeString();
    tmp.heureFin = new Date(this.event.heureFin).toLocaleTimeString();
    this.eventsService.addEvent(tmp); // Add Event to db
    this.router.navigateByUrl('/menu/evenements'); // Go back EvenementsPage when Event add to db successful
    this.evenements.ionViewWillEnter(); // Reload Events List from EvenementsPage
  }

}
