import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangelogCreateComponent } from './changelog-create.component';

describe('ChangelogCreateComponent', () => {
  let component: ChangelogCreateComponent;
  let fixture: ComponentFixture<ChangelogCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangelogCreateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangelogCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
