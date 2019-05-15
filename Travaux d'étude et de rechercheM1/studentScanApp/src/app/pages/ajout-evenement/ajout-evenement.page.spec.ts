import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AjoutEvenementPage } from './ajout-evenement.page';

describe('AjoutEvenementPage', () => {
  let component: AjoutEvenementPage;
  let fixture: ComponentFixture<AjoutEvenementPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AjoutEvenementPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AjoutEvenementPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
