import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FranjaComponent } from './franja.component';

describe('FranjaComponent', () => {
  let component: FranjaComponent;
  let fixture: ComponentFixture<FranjaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FranjaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FranjaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
