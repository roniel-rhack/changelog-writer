import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangelogDisplayComponent } from './changelog-display.component';

describe('ChangelogDisplayComponent', () => {
  let component: ChangelogDisplayComponent;
  let fixture: ComponentFixture<ChangelogDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangelogDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangelogDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
