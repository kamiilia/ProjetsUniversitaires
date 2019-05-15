import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Event} from '../../models/event';
import {ScanAnonyme} from '../../models/scan-anonyme';
import {Platform} from '@ionic/angular';
import {EvenementsService} from '../../services/evenements.service';
import {File} from '@ionic-native/file/ngx';
import { EmailComposer } from '@ionic-native/email-composer/ngx';
import { AlertController,NavController } from '@ionic/angular';
import {StatsTabPage} from '../stats-tab/stats-tab.page';
import { Router } from '@angular/router';
@Component({
  selector: 'app-evenement',
  templateUrl: './evenement.page.html',
  styleUrls: ['./evenement.page.scss'],
})
export class EvenementPage implements OnInit {

  titre: string;
  event: Event;
  listScan: ScanAnonyme[];
  stats: StatsTabPage;
  constructor(private router: Router, private alertController: AlertController, private emailComposer: EmailComposer, private file: File, private route: ActivatedRoute, private platform: Platform, private eventsService: EvenementsService) {
    this.platform.ready().then(() => {
      this.titre = this.route.parent.snapshot.parent.paramMap.get('titre');// get the event title
      this.listScan = [];
      this.event = new Event();
      this.eventsService.createDatabaseFile().then((db) => {
        this.eventsService.getEvent(this.titre).then((data: any) => {// get current event
          this.event = data;
          this.eventsService.getScans(this.event.id).then((data2: any) => {// get all scan performed during the current event
            this.listScan = data2;
          });
        });
      });
    });
   }

statistique(){// call ionViewWillEnter method in stat page to create or refresh the graphique
    this.stats.ionViewWillEnter();

  }
  public email(){
    this.sendmail();
  }

   async sendmail() {
   const alert = await this.alertController.create({// alert send email
     header: 'Envoyer mail a : ',
     inputs: [
       {
         name: 'mail',
         type: 'text',
         value:'moulaouikamilia@gmail.com'
       },
     ],
     buttons: [
       {
         text: 'Quitter',
         role: 'cancel',
         cssClass: 'secondary',
         handler: () => {
           console.log('Confirm Cancel');
         }
       }, {
         text: 'envoyer',
         handler: (data) => {
           if(data.mail){ // check invalid email
             if (!/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,3}))$/.test(data.mail)) {

               this.mailinvalide();
             }else{
             this.envoiemail(data.mail);
             }
           }else{ // when empty email input
           this.mailvide();}
         }
       }
     ]
   });
   await alert.present();
 }

 public mailinvalide(){// alert of invalid mail
   alert('adresse mail invalide ');
   this.sendmail();
 }

 public mailvide(){ // alert of empty email input
   alert('Veuillez saisir une adresse mail');
   this.sendmail();
 }

  public saveAsCSV(){ // generate and save csv file on phone memory
    var csv = "idScan;idEvent;Tag;Date;Heure\n";
    this.listScan = [];
    this.eventsService.getScans(this.event.id).then((data3:any) => {// get all scans of the current event

      if(data3 == null) {
         return;
       }
       for(var i=0; i < data3.length;i++){// for each scan
        this.listScan.push(data3[i].id,data3[i].eventId,data3[i].tag,data3[i].date,data3[i].heure);
       }


     for (var i = 0; i < (this.listScan.length / 5); i++) { // construction of the csv file
      csv += this.listScan[i * 5] + ";" + this.listScan[i*5 + 1] + ";" + this.listScan[i*5 + 2]+";" + this.listScan[i*5 + 3] + ";" + this.listScan[i*5 + 4] + "\n";
    }
    this.save(csv);
     });
 }
 async save(csv) {// save the csv file as data.csv
   await this.platform.ready();
   return this.file.writeFile(this.file.externalRootDirectory, 'data.csv', csv, {replace:true});

 }




public envoiemail(destinataire:string){ // send the Email
   this.saveAsCSV();
   this.emailComposer.isAvailable().then((available: boolean) =>{// if the emailComposer app is available
  if(available) {}});
 let email = {// construction of the email
   to: destinataire,
   cc: '',
   attachments: ['file:///sdcard/data.csv'],// attach the csv saved
   subject: 'Recapitulatif CSV',
   body: 'Ci-joint le fichier csv constituant les informations des carte scannées',
   isHtml: true};
this.emailComposer.open(email);}// open and attach email to emailComposer


  ngOnInit() {

    this.titre = this.route.snapshot.params['titre'];

  }

}
