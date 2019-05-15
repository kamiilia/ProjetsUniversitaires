import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PresencePage } from './presence.page';

describe('PresencePage', () => {
  let component: PresencePage;
  let fixture: ComponentFixture<PresencePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PresencePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PresencePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
