import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScanTabPage } from './scan-tab.page';

describe('ScanTabPage', () => {
  let component: ScanTabPage;
  let fixture: ComponentFixture<ScanTabPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScanTabPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScanTabPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
