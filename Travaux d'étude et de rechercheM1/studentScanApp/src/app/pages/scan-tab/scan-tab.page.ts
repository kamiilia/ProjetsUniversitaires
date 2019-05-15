import {Component, OnDestroy, OnInit} from '@angular/core';
import {NFC} from '@ionic-native/nfc/ngx';
import {ModalController, Platform} from '@ionic/angular';
import {ActivatedRoute} from '@angular/router';
import {EvenementsService} from '../../services/evenements.service';
import { Event } from '../../models/event';
import {ScanAnonyme} from '../../models/scan-anonyme';
import {Subscription} from 'rxjs';
import {ValideModalComponent} from '../valide-modal/valide-modal.component';
import {DeniedModalComponent} from '../denied-modal/denied-modal.component';

@Component({
  selector: 'app-scan-tab',
  templateUrl: './scan-tab.page.html',
  styleUrls: ['./scan-tab.page.scss'],
})
export class ScanTabPage implements OnInit, OnDestroy {
  tagId: string;
  date: string;
  time: string;
  titre: string;
  scanned: boolean;
  event: Event;
  listScan: ScanAnonyme[];
  subscribe: Subscription;

  constructor(private nfc: NFC, private platform: Platform, private route: ActivatedRoute, private eventsService: EvenementsService, public modalController: ModalController) {
    this.platform.ready().then(() => {
      this.titre = this.route.parent.snapshot.parent.paramMap.get('titre');
      this.listScan = [];
      this.event = new Event();
      this.eventsService.createDatabaseFile().then((db) => {
        this.eventsService.getEvent(this.titre).then((data: any) => { //get Current Event
          this.event = data;
          this.eventsService.getScans(this.event.id).then((data2: any) => { //get Scans from current event
            this.listScan = data2;
          });
        });
      });
      this.scanned = false;
      this.nfc.enabled().then((resolve) => {
        this.ListenDataFormatableNfc().then((sub) => {
          this.subscribe = sub;
        });
      }).catch((error) => {
        alert('NFC désactiver, Veuillez l\'activer');
      });
    });
  }


  private ListenDataFormatableNfc(): Promise<Subscription> { //open nfc Listener
    return new Promise((resolve) => {
      const sub = this.nfc.addNdefFormatableListener(nfcEvent => this.sesReadNFC(nfcEvent.tag)).subscribe(data => {
        if (data && data.tag && data.tag.id) {
          const tagId = this.nfc.bytesToHexString(data.tag.id);
          if (tagId) {
            const tmp = new ScanAnonyme();
            tmp.tag = tagId;
            tmp.date = new Date().toLocaleDateString();
            tmp.heure = new Date().toLocaleTimeString();
            tmp.eventId = this.event.id;
            this.eventsService.addScan(tmp); //save scan to dataBase
            this.presentModal(tmp); //load feedback ValideComponent
            this.tagId = tagId;
            this.date = new Date().toLocaleDateString();
            this.time = new Date().toLocaleTimeString();
            this.scanned = true;
            this.listScan = [];
            this.eventsService.getScans(this.event.id).then((data2: any) => {
              this.listScan = data2;
            });
          }else {
            this.presentDeniedModal(new Date().toLocaleDateString(), new Date().toLocaleTimeString()); //load feedbak DeniedComponent
          }
        } else {
          this.presentDeniedModal(new Date().toLocaleDateString(), new Date().toLocaleTimeString()); // load feedbak DeniedComponent
        }
      });
      resolve(sub); //return Subscription
    });
  }

  sesReadNFC(data): void {}

  async presentModal(scan: ScanAnonyme) { //load ValideComponent when scan is succesful
    const modal = await this.modalController.create({
      component: ValideModalComponent,
      componentProps: { 'tag': scan.tag, 'date': scan.date, 'heure': scan.heure },
      cssClass: 'mymodal'
    });
    return await modal.present();
  }

  async presentDeniedModal(date: string, heure: string) { //load DeniedComponent when scan is wrong
    const modal = await this.modalController.create({
      component: DeniedModalComponent,
      componentProps: {'date': date, 'heure': heure },
      cssClass: 'mymodal'
    });
    return await modal.present();
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subscribe.unsubscribe();
  }
}
