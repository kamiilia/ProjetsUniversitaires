import { Component, OnInit } from '@angular/core';
import {ModalController} from '@ionic/angular';

@Component({
  selector: 'app-valide-modal-mail',
  templateUrl: './valide-modal-mail.component.html',
  styleUrls: ['./valide-modal-mail.component.scss'],
})
export class ValideModalMailComponent implements OnInit {
  destinataire : string;
  src:string;
  constructor(public modalController: ModalController) {

    this.src = '../../../../assets/ok.png';
  }

  public close(){
    this.modalController.dismiss();
  }

  ngOnInit() {}

}
