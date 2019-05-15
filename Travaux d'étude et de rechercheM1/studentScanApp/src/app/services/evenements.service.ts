import { Injectable } from '@angular/core';
import { SQLite, SQLiteObject } from '@ionic-native/sqlite/ngx';
import { Platform } from '@ionic/angular';
import { Event } from '../models/event';
import { ScanAnonyme } from '../models/scan-anonyme';

const DATABASE_FILE_NAME = 'events.db';

@Injectable({
  providedIn: 'root'
})
export class EvenementsService {

  private db: SQLiteObject;

  constructor(private sqlite: SQLite, private platform: Platform) {
  }

  createDatabaseFile() {
    return new Promise((resolve) => {
      this.sqlite.create({
        name: DATABASE_FILE_NAME,
        location: 'default'
      })
          .then((db: SQLiteObject) => {
            this.db = db;
            this.db.executeSql('PRAGMA foreign_keys = ON;', []).catch();
            this.createTable();
            this.createScanTable();
            resolve(db);
          })
          .catch(e => console.log(e));
    });
  }

  private createTable(): void {
    this.db.executeSql('CREATE TABLE IF NOT EXISTS EVENEMENTS (event_id INTEGER PRIMARY KEY AUTOINCREMENT,' +
      'name VARCHAR(50) NOT NULL UNIQUE,' +
      'startDate VARCHAR(50) NOT NULL,' +
      'endDate VARCHAR(50) NOT NULL,' +
      'heureDebut VARCHAR(50) NOT NULL,'+
      'heureFin VARCHAR(50) NOT NULL,'+
      ' description VARCHAR(50))', [])
      .catch(e => console.log('Error'));
  }

  public createScanTable() {// scan table
      this.db.executeSql('CREATE TABLE IF NOT EXISTS SCANS (' +
          'scan_id INTEGER PRIMARY KEY AUTOINCREMENT,' +
          'tag VARCHAR(50) NOT NULL,' +
          'date VARCHAR(50) NOT NULL,' +
          'heure VARCHAR(50) NOT NULL,' +
          'event_id INTEGER NOT NULL,' +
          'FOREIGN KEY (event_id)' +
          'REFERENCES EVENEMENTS(event_id)' +
          'ON DELETE CASCADE)', [])
          .catch(e => console.log('Prout'));
  }
  public getCreneau(id: number){// used when the event last 2 or more days
    return new Promise((resolve) => {
        this.db.executeSql('SELECT startDate,endDate FROM EVENEMENTS WHERE EVENEMENTS.event_id=?', [id])
            .then((data) => {
              const tmp = new Event();
              tmp.dateDebut = data.rows.item(0).startDate;
              tmp.dateFin = data.rows.item(0).endDate;
              resolve(tmp);
            })
            .catch(e => console.log(e));
    });
  }

  public getEvents() {
      return new Promise((resolve) => {
        this.db.executeSql('SELECT * FROM EVENEMENTS', [])
            .then((data) => {
              const list = [];
              for (let i = 0; i < data.rows.length; i++ ) {
                const tmp = new Event();
                tmp.id = data.rows.item(i).event_id;
                tmp.name = data.rows.item(i).name;
                tmp.dateDebut = data.rows.item(i).startDate;
                tmp.dateFin = data.rows.item(i).endDate;
                tmp.description = data.rows.item(i).description;
                tmp.heureDebut = data.rows.item(i).heureDebut;
                tmp.heureFin = data.rows.item(i).heureFin;
                list.push(tmp);
              }
              resolve(list);
            }).catch(e => alert(e));
      });
}
  public getEvent(name: string) {
    return new Promise((resolve) => {
        this.db.executeSql('SELECT * FROM EVENEMENTS WHERE name=?', [name])
            .then((data) => {
                const tmp = new Event();
                tmp.id = data.rows.item(0).event_id;
                tmp.name = data.rows.item(0).name;
                tmp.dateDebut = data.rows.item(0).startDate;
                tmp.dateFin = data.rows.item(0).endDate;
                tmp.description = data.rows.item(0).description;
                tmp.heureDebut = data.rows.item(0).heureDebut;
                tmp.heureFin = data.rows.item(0).heureFin;
                resolve(tmp);
            });
    });
  }

  public viderliste() { // delete scan and event content
    this.db.executeSql('DELETE FROM EVENEMENTS', [])
        .then((data) => {
            this.db.executeSql('DELETE FROM SCANS', [])
                .then(() => this.droptable());
        } )
        . catch(e => console.log(e));

  }

  public droptable() { // drop scan and event tables
    this.db.executeSql('DROP TABLE IF EXISTS EVENEMENTS', [])
        .then(() => {
            this.db.executeSql('DROP TABLE IF EXISTS SCANS', [])
                .then(() => this.createTable());
        })
        .catch(e => console.log(e));
  }

  public suppEvent(id: number) {
    this.db.executeSql('DELETE FROM EVENEMENTS WHERE event_id=?', [id])
        .then( () => {})
        .catch(e => console.log(e));
  }

  public addEvent(event: Event) {
    this.db.executeSql('INSERT INTO EVENEMENTS (name,startDate,endDate,description,heureDebut,heureFin) VALUES(?,?,?,?,?,?)',
        [event.name, event.dateDebut, event.dateFin, event.description, event.heureDebut, event.heureFin])
        .then(() => {})
        .catch(e => console.log('Error'));
  }

  public addScan(scan: ScanAnonyme) { // insert a new scan to scan table
      this.db.executeSql('INSERT INTO SCANS (tag,date,heure,event_id) VALUES(?,?,?,?)', [scan.tag, scan.date, scan.heure, scan.eventId])
          .catch(e => console.log('Error'));
  }
  public getscansdate(d :string,id: number){// get all scans performed during the id event and on the d day
    return new Promise((resolve) => {
        this.db.executeSql('SELECT * FROM SCANS NATURAL JOIN EVENEMENTS WHERE EVENEMENTS.event_id=? AND SCANS.date=?', [id,d])
            .then((data) => {
              const list = [];
              for (let i = 0; i < data.rows.length; i++) {//construction of a list of scan object
                  const tmp = new ScanAnonyme();
                  tmp.id = data.rows.item(i).scan_id;
                  tmp.eventId = data.rows.item(i).event_id;
                  tmp.tag = data.rows.item(i).tag;
                  tmp.date = data.rows.item(i).date;
                  tmp.heure = data.rows.item(i).heure;
                  list.push(tmp);
              }
              resolve(list);
            });
    });
  }

  public getScans(id: number) { // get all scans performed on the id event
      return new Promise((resolve) => {
          this.db.executeSql('SELECT * FROM SCANS NATURAL JOIN EVENEMENTS WHERE EVENEMENTS.event_id=?', [id])
              .then((data) => {
                  const list = [];
                  for (let i = 0; i < data.rows.length; i++) {// coninstruction of a list of scan object
                      const tmp = new ScanAnonyme();
                      tmp.id = data.rows.item(i).scan_id;
                      tmp.eventId = data.rows.item(i).event_id;
                      tmp.tag = data.rows.item(i).tag;
                      tmp.date = data.rows.item(i).date;
                      tmp.heure = data.rows.item(i).heure;
                      list.push(tmp);
                  }
                  resolve(list);
              })
              .catch(e => console.log(e));
      });
  }

    public getAllScans() {// get all scans of the whole events
        return new Promise((resolve) => {
            this.db.executeSql('SELECT * FROM SCANS', [])
                .then((data) => {
                    const list = [];
                    for (let i = 0; i < data.rows.length; i++) {
                        const tmp = new ScanAnonyme();
                        tmp.id = data.rows.item(i).scan_id;
                        tmp.eventId = data.rows.item(i).event_id;
                        tmp.tag = data.rows.item(i).tag;
                        tmp.date = data.rows.item(i).date;
                        tmp.heure = data.rows.item(i).heure;
                        list.push(tmp);
                    }
                    resolve(list);
                })
                .catch(e => console.log(e));
        });
    }
}
