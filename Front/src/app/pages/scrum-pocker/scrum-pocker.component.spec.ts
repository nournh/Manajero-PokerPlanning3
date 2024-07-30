import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ScrumPockerComponent } from './scrum-pocker.component';

describe('ScrumPockerComponent', () => {
  let component: ScrumPockerComponent;
  let fixture: ComponentFixture<ScrumPockerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ScrumPockerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ScrumPockerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
