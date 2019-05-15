import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatsTabPage } from './stats-tab.page';

describe('StatsTabPage', () => {
  let component: StatsTabPage;
  let fixture: ComponentFixture<StatsTabPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatsTabPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatsTabPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
