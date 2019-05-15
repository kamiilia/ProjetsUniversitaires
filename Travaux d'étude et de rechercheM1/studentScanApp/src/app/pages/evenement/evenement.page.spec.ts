import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EvenementPage } from './evenement.page';

describe('EvenementPage', () => {
  let component: EvenementPage;
  let fixture: ComponentFixture<EvenementPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EvenementPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EvenementPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
