import { Component, OnInit } from '@angular/core';
import {ModalController} from '@ionic/angular';

@Component({
  selector: 'app-denied-modal',
  templateUrl: './denied-modal.component.html',
  styleUrls: ['./denied-modal.component.scss'],
})
export class DeniedModalComponent implements OnInit {
  date: string;
  heure: string;
  src: string;

  constructor(public modalController: ModalController) {

    this.src = '../../../assets/error.png';
  }

  public close(){
    this.modalController.dismiss();
  }

  ngOnInit() {}

}
