import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvenementsPage } from './evenements.page';

describe('EvenementsPage', () => {
  let component: EvenementsPage;
  let fixture: ComponentFixture<EvenementsPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvenementsPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvenementsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
