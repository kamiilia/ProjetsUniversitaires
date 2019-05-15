import { Component, OnInit } from '@angular/core';
import {ModalController} from '@ionic/angular';

@Component({
  selector: 'app-valide-modal',
  templateUrl: './valide-modal.component.html',
  styleUrls: ['./valide-modal.component.scss'],
})
export class ValideModalComponent implements OnInit {
  tag: string;
  date: string;
  heure: string;
  src: string;

  constructor(public modalController: ModalController) {

    this.src = '../../../assets/ok.png';
  }

  public close(){
    this.modalController.dismiss();
  }

  ngOnInit() {}

}
