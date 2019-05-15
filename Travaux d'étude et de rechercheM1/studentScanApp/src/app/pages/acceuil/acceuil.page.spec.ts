import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AcceuilPage } from './acceuil.page';

describe('AcceuilPage', () => {
  let component: AcceuilPage;
  let fixture: ComponentFixture<AcceuilPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AcceuilPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AcceuilPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
