import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AusenciaEliminarComponent } from './ausencia-eliminar.component';

describe('AusenciaEliminarComponent', () => {
  let component: AusenciaEliminarComponent;
  let fixture: ComponentFixture<AusenciaEliminarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AusenciaEliminarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AusenciaEliminarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
