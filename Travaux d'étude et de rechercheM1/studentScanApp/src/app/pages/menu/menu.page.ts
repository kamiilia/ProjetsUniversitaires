import { Component, OnInit } from '@angular/core';
import {Router, RouterEvent} from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
})
export class MenuPage implements OnInit {
  selectedPath = '';

  pages = [ //Pages attached to the menu
    {
      title: 'Accueil',
      url: '/menu/acceuil'
    },
    {
      title: 'Relevé de présence',
      url: '/menu/presence'
    },
    {
      title: 'Événements',
      url: '/menu/evenements'
    }
  ];

  constructor(private router: Router) {
    this.router.events.subscribe((event: RouterEvent) => {
      if (event && event.url) {
        this.selectedPath = event.url; //Get the current page
      }
    });
  }

  ngOnInit() {
  }

}
