import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Event} from '../../models/event';
import {ScanAnonyme} from '../../models/scan-anonyme';
import {Platform} from '@ionic/angular';
import {EvenementsService} from '../../services/evenements.service';
import * as HighCharts from 'highcharts';
@Component({
  selector: 'app-stats-tab',
  templateUrl: './stats-tab.page.html',
  styleUrls: ['./stats-tab.page.scss'],
})
export class StatsTabPage implements OnInit {// statistic interface

  titre: string;
  event: Event;
  listScan: ScanAnonyme[];
  options: any;
  eventStarttime: number;
  eventEndtime: number;
  heured:any;
  heuref:any;
  a:any;
  listescanee:any[];

  constructor(private route: ActivatedRoute, private platform: Platform, private eventsService: EvenementsService ) {
    this.platform.ready().then(() => {
      this.refresh();// if first execution
  });
}
refresh(){// refresh the statistics
  this.titre = this.route.parent.snapshot.parent.paramMap.get('titre');
  this.listScan = [];
  this.event = new Event();
  this.eventsService.createDatabaseFile().then((db) => {
    this.eventsService.getEvent(this.titre).then((data: any) => {// get current event
      this.event = data;
      this.eventStarttime=parseInt(this.event.heureDebut);// get start time of the current event
      this.eventEndtime=parseInt(this.event.heureFin); // get end time of the current event
      this.eventsService.getscansdate(this.event.dateDebut, this.event.id).then((data1: any) => {
        this.a=data1;// a list of all scans performed during the current event
        this.scansNumber(data1);
          this.options = {
            chart: {
               type: 'column'},
             title: {
               text: 'Statistique des Scans effectués '},
             xAxis: {
               min:this.eventStarttime,// start time of the event
               max:this.eventEndtime,// end time of the event
               tickInterval:1, // make an interval of 1 hour between each
               title: {
                 text: 'Durée de l\'évenement'}},
             yAxis: {
               title: {
                 text: 'Nombre de cartes'},
               min:0,},
             series: [{
               name: 'Nombre cartes scannées',
               data: this.listescanee, // return the number of scan list
             }]};
        HighCharts.chart('container',this.options);// create the graphic
        });
      });
    });
}

ionViewWillEnter(){//refresh the graphic
  this.refresh();
}
scansNumber(data:any){ // get an array of number of scans performed from 00h to 23h during an hour
  const list = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];// indices represents the 24hours
// for each scan

  for (let i = 0; i < data.length; i++) {
    // if its hour of scan is between  00h and 01h
    //increment index 0 of the list table
      if(data[i].heure>"00:00:00" && data[i].heure<"00:59:59"){
        list[0]+=1;
      }
      if(data[i].heure>"01:00:00" && data[i].heure<"01:59:59"){
        list[1]+=1;
      }
      if(data[i].heure>"02:00:00" && data[i].heure<"02:59:59" ){
        list[2]+=1;
      }
      if(data[i].heure>"03:00:00" && data[i].heure<"03:59:59"){
        list[3]+=1;
      }
      if(data[i].heure>"04:00:00" && data[i].heure<"04:59:59"){
        list[4]+=1;
      }
      if(data[i].heure>"05:00:00" && data[i].heure<"05:59:59" ){
        list[5]+=1;
      }
      if(data[i].heure>"06:00:00" && data[i].heure<"06:59:59"){
        list[6]+=1;
      }
      if(data[i].heure>"07:00:00" && data[i].heure<"07:59:59"){
        list[7]+=1;
      }
      if(data[i].heure>"08:00:00" && data[i].heure<"08:59:59" ){
        list[8]+=1;
      }
      if(data[i].heure>"09:00:00" && data[i].heure<"09:59:59"){
        list[9]+=1;
      }
      if(data[i].heure>"10:00:00" && data[i].heure<"10:59:59"){
        list[10]+=1;
      }
      if(data[i].heure>"11:00:00" && data[i].heure<"11:59:59" ){
        list[11]+=1;
      }
      if(data[i].heure>"12:00:00" && data[i].heure<"12:59:59"){
        list[12]+=1;
      }
      if(data[i].heure>"13:00:00" && data[i].heure<"13:59:59"){
        list[13]+=1;
      }
      if(data[i].heure>"14:00:00" && data[i].heure<"14:59:59" ){
        list[14]+=1;
      }
      if(data[i].heure>"15:00:00" && data[i].heure<"15:59:59"){
        list[15]+=1;
      }
      if(data[i].heure>"16:00:00" && data[i].heure<"16:59:59"){
        list[16]+=1;
      }
      if(data[i].heure>"17:00:00" && data[i].heure<"17:59:59" ){
        list[17]+=1;
      }
      if(data[i].heure>"18:00:00" && data[i].heure<"18:59:59"){
        list[18]+=1;
      }
      if(data[i].heure>"19:00:00" && data[i].heure<"19:59:59"){
        list[19]+=1;
      }
      if(data[i].heure>"20:00:00" && data[i].heure<"20:59:59" ){
        list[20]+=1;
      }
      if(data[i].heure>"21:00:00" && data[i].heure<"21:59:59"){
        list[21]+=1;
      }
      if(data[i].heure>"22:00:00" && data[i].heure<"22:59:59"){
        list[22]+=1;
      }
      if(data[i].heure>"23:00:00" && data[i].heure<"23:59:59" ){
        list[23]+=1;
      }
  }
this.listescanee=list; // return the number of scan list
}

  ngOnInit() {

  }



}
