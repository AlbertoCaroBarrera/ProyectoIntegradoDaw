import { Component, OnInit } from '@angular/core';
import { PrimengModule } from '../../primeng.module';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Horario } from '../../clases/horarios';
import { HorarioService } from '../../services/horario/horario.service';
import { User } from '../../clases/user';
import { Franja } from '../../clases/franja';
import { Asignatura } from '../../clases/asignatura';
import { Aula } from '../../clases/aula';
import { Grupo } from '../../clases/grupo';
import { Periodo } from '../../clases/periodo';
import { FormsModule } from '@angular/forms';
import { ProfesorService } from '../../services/profesor/profesor.service';
import { AsignaturaService } from '../../services/asignatura/asignatura.service';
import { FranjaService } from '../../services/franja/franja.service';
import { AulaService } from '../../services/aula/aula.service';
import { GrupoService } from '../../services/grupo/grupo.service';
import { PeriodoService } from '../../services/periodo/periodo.service';
import { SpinnerComponent } from "../spinner/spinner.component";
import { forkJoin } from 'rxjs';
import { PanelModule } from 'primeng/panel';
import { UserStateService } from '../../services/user/userState.service';
@Component({
  selector: 'app-horario',
  standalone: true,
  templateUrl: './horario.component.html',
  styleUrls: ['./horario.component.css'],
  providers: [MessageService, ConfirmationService],
  imports: [PrimengModule, FormsModule, SpinnerComponent,PanelModule ]
})
export class HorarioComponent implements OnInit {
  horarios!: Horario[];
  filteredHorarios!: Horario[];
  selectedHorarios!: Horario[] | null;
  horario!: Horario;
  horarioDialog: boolean = false;
  submitted: boolean = false;
  profesoresDisponibles!: User[];
  asignaturasDisponibles!: Asignatura[];
  franjasDisponibles!: Franja[];
  aulasDisponibles!: Aula[];
  gruposDisponibles!: Grupo[];
  periodosDisponibles!: Periodo[];
  loading: boolean = true;
  selectedProfesor!: User | null;
  selectedFranja!: Franja | null;
  selectedAsignatura!: Asignatura | null;
  selectedAula!: Aula | null;
  selectedGrupo!: Grupo | null;
  selectedDia!: string | null;
  diasDisponibles!: string[];
  advancedFiltersCollapsed: boolean = true;
  isAdmin: boolean = false;

  constructor(
    private horarioSevice: HorarioService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private profesorService: ProfesorService,
    private asignaturaService: AsignaturaService,
    private franjaService: FranjaService,
    private aulaService: AulaService,
    private grupoService: GrupoService,
    private periodoService: PeriodoService,
    private userStateService: UserStateService
  ) { }

  ngOnInit(): void {
    this.cargarDatos();

    this.userStateService.user.subscribe((user: User | null) => {
      if (user) {
        this.isAdmin = user.rol === 'ADMIN';
      } else {
        this.isAdmin = false;
      }
    });
  }

  cargarDatos() {
    this.loading = true;

    forkJoin({
      horarios: this.horarioSevice.getHorarios(),
      profesores: this.profesorService.getProfesores(),
      asignaturas: this.asignaturaService.getAsignaturas(),
      franjas: this.franjaService.getFranjas(),
      aulas: this.aulaService.getAulas(),
      grupos: this.grupoService.getGrupos(),
      periodos: this.periodoService.getPeriodos()
    }).subscribe(({ horarios, profesores, asignaturas, franjas, aulas, grupos, periodos }) => {
      this.horarios = horarios;
      this.filteredHorarios = horarios; 
      this.profesoresDisponibles = profesores;
      this.asignaturasDisponibles = asignaturas;
      this.franjasDisponibles = franjas;
      this.aulasDisponibles = aulas;
      this.gruposDisponibles = grupos;
      this.periodosDisponibles = periodos;
      this.diasDisponibles = this.getDiasDisponibles(horarios);
      this.loading = false;
    });
}

getDiasDisponibles(horarios: Horario[]): string[] {
    const dias = horarios.map(horario => horario.dia);
    return Array.from(new Set(dias)); 
}



  openNew() {
    this.horario = {
      profesor: new User(),
      dia: "",
      franja: new Franja(),
      asignatura: new Asignatura(),
      aula: new Aula(),
      grupo: new Grupo(),
      periodo: new Periodo()
    };
    this.submitted = false;
    this.horarioDialog = true;
  }

  deleteSelectedHorarios() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected horarios?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.horarios = this.horarios.filter((val) => !this.selectedHorarios?.includes(val));
        this.filteredHorarios = this.filteredHorarios.filter((val) => !this.selectedHorarios?.includes(val));
        this.selectedHorarios = null;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Horarios Deleted', life: 3000 });
      }
    });
  }

  editHorario(horario: Horario) {
    this.horario = { ...horario };
    this.horarioDialog = true;
  }

  deleteHorario(horario: Horario) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.horarioSevice.deleteHorario(horario.id).subscribe(() => {
          this.cargarDatos();
        });
        this.horarios = this.horarios.filter((val) => val.id !== horario.id);
        this.filteredHorarios = this.filteredHorarios.filter((val) => val.id !== horario.id);
        this.horario = {
          profesor: new User(),
          dia: "",
          franja: new Franja(),
          asignatura: new Asignatura(),
          aula: new Aula(),
          grupo: new Grupo(),
          periodo: new Periodo()
        };
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Horario Borrada', life: 3000 });
      }
    });
  }

  hideDialog() {
    this.horarioDialog = false;
    this.submitted = false;
  }

  saveHorario() {
    this.submitted = true;

    if (this.horario.id) {
      this.horarioSevice.updateHorarios(this.horario).subscribe(() => {
        this.cargarDatos();
      });
      this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Horario Cambiada', life: 3000 });
    } else {
      this.horarioSevice.addHorarios(this.horario).subscribe(() => {
        this.cargarDatos();
      });
      this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Horario Creada', life: 3000 });
    }

    this.horarios = [...this.horarios];
    this.filteredHorarios = [...this.horarios];
    this.horarioDialog = false;
    this.horario = {
      profesor: new User(),
      dia: "",
      franja: new Franja(),
      asignatura: new Asignatura(),
      aula: new Aula(),
      grupo: new Grupo(),
      periodo: new Periodo()
    };
  }

  findIndexById(id: number): number {
    let index = -1;
    for (let i = 0; i < this.horarios.length; i++) {
      if (this.horarios[i].id === id) {
        index = i;
        break;
      }
    }

    return index;
  }

  filterByCriteria() {
    this.filteredHorarios = this.horarios;

    if (this.selectedProfesor) {
        this.filteredHorarios = this.filteredHorarios.filter(horario => horario.profesor.nombre === this.selectedProfesor?.nombre);
    }

    if (this.selectedDia) {
        this.filteredHorarios = this.filteredHorarios.filter(horario => horario.dia === this.selectedDia);
    }

    if (this.selectedFranja) {
        this.filteredHorarios = this.filteredHorarios.filter(horario => horario.franja.descripcion === this.selectedFranja?.descripcion);
    }

    if (this.selectedAsignatura) {
        this.filteredHorarios = this.filteredHorarios.filter(horario => horario.asignatura.descripcion === this.selectedAsignatura?.descripcion);
    }

    if (this.selectedAula) {
        this.filteredHorarios = this.filteredHorarios.filter(horario => horario.aula?.descripcion === this.selectedAula?.descripcion);
    }

    if (this.selectedGrupo) {
        this.filteredHorarios = this.filteredHorarios.filter(horario => horario.grupo.descripcion === this.selectedGrupo?.descripcion);
    }
}



  filterByProfesor(profesor: User | null) {
    if (profesor) {
      this.filteredHorarios = this.horarios.filter(horario => horario.profesor.nombre === profesor.nombre);
    } else {
      this.filteredHorarios = [...this.horarios];
    }
  }

  clearFilters() {
    this.selectedProfesor = null;
    this.selectedDia = null;
    this.selectedFranja = null;
    this.selectedAsignatura = null;
    this.selectedAula = null;
    this.selectedGrupo = null;
    this.filteredHorarios = [...this.horarios];
}

toggleAdvancedFilters() {
  this.advancedFiltersCollapsed = !this.advancedFiltersCollapsed;
}


}
