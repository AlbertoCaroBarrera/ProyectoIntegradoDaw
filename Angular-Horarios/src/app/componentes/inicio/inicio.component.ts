import { Component } from '@angular/core';
import { PrimengModule } from '../../primeng.module';

import { HorarioService } from '../../services/horario/horario.service';
import { FranjaService } from '../../services/franja/franja.service';
import { Franja } from '../../clases/franja';
import { map } from 'rxjs/operators';
import { Horario } from '../../clases/horarios';
import { SpinnerComponent } from '../spinner/spinner.component';
import { User } from '../../clases/user';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [PrimengModule, SpinnerComponent],
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {
  loading: boolean = false;
  franjasDisponibles: Franja[] = [];
  horarios: Horario[] = [];
  horariosPorFranja: any[] = [];
  errorMessage: any;
  user!: User;
  token: any = sessionStorage.getItem("token")

  constructor(
    private horarioService: HorarioService,
    private franjaService: FranjaService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe({
      next: (userData) => {
        if (userData) {
          this.user = userData;
        }

      },
      error: (errorData) => {
        this.errorMessage = errorData
      },
      complete: () => {
        console.info("User Data ok");
      }
    })
    if (this.token) {
      this.loading = true;
      this.getFranja();
      this.getHorarios();

    }
  }

  getFranja() {
    this.franjaService.getFranjas().subscribe(data => this.franjasDisponibles = data);
  }

  getHorarios() {
    this.horarioService.getHorarios().pipe(
      map(horarios => horarios.filter(horario => horario.profesor.professor_cod === this.user.professor_cod))
    ).subscribe(filteredHorarios => {
      this.horarios = filteredHorarios;
      this.organizarHorariosPorFranja();
    });

  }

  organizarHorariosPorFranja() {
    this.horariosPorFranja = this.franjasDisponibles.map(franja => {
      return {
        descripcion: franja.descripcion,
        horarios: {
          L: this.horarios.filter(horario => horario.dia === 'L' && horario.franja.id === franja.id),
          M: this.horarios.filter(horario => horario.dia === 'M' && horario.franja.id === franja.id),
          X: this.horarios.filter(horario => horario.dia === 'X' && horario.franja.id === franja.id),
          J: this.horarios.filter(horario => horario.dia === 'J' && horario.franja.id === franja.id),
          V: this.horarios.filter(horario => horario.dia === 'V' && horario.franja.id === franja.id)
        }
      };
    });
    this.loading = false;
  }

}