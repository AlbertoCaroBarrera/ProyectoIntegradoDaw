import { Component, OnInit } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PrimengModule } from '../../primeng.module';
import { FormsModule } from '@angular/forms';
import { User, Role } from '../../clases/user';
import { ProfesorService } from '../../services/profesor/profesor.service';
import { UserStateService } from '../../services/user/userState.service';

@Component({
  selector: 'app-profesor',
  standalone: true,
  imports: [PrimengModule, FormsModule],
  templateUrl: './profesor.component.html',
  styleUrls: ['./profesor.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class ProfesorComponent implements OnInit {
  profesores!: User[];
  selectedProfesores!: User[] | null;
  profesor: User = new User(); 
  profesorDialog: boolean = false;
  submitted: boolean = false;
  roles: any[];
  isAdmin: boolean = false;

  constructor(
    private profesorService: ProfesorService, 
    private messageService: MessageService, 
    private confirmationService: ConfirmationService,
    private userStateService: UserStateService) {
    this.roles = [
      { label: 'Admin', value: Role.ADMIN },
      { label: 'Profesor', value: Role.PROFESOR }
    ];
  }

  ngOnInit(): void {
    this.userStateService.user.subscribe((user: User | null) => {
      if (user) {
        this.isAdmin = user.rol === 'ADMIN';
      } else {
        this.isAdmin = false;
      }
    });
    
    this.getProfesores();
  }

  getProfesores() {
    this.profesorService.getProfesores().subscribe(data => this.profesores = data);
  }

  openNew() {
    this.profesor = new User();
    this.submitted = false;
    this.profesorDialog = true;
  }

  deleteSelectedProfesores() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected profesores?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.selectedProfesores?.forEach(profesor => {
          this.profesorService.deleteProfesor(profesor.professor_cod).subscribe(() => {
            this.getProfesores();
          });
        });
        this.selectedProfesores = null;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Profesores Deleted', life: 3000 });
      }
    });
  }

  editProfesor(profesor: User) {
    this.profesor = { ...profesor };
    this.profesorDialog = true;
  }

  deleteProfesor(profesor: User) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + profesor.nombre + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.profesorService.deleteProfesor(profesor.professor_cod).subscribe(() => {
          this.getProfesores();
        });
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Profesor Deleted', life: 3000 });
      }
    });
  }

  hideDialog() {
    this.profesorDialog = false;
    this.submitted = false;
  }

  saveProfesor() {
    this.submitted = true;
  
    if (this.profesor.nombre?.trim()) {
      const simpleProfesor = {
        professor_cod: this.profesor.professor_cod,
        nombre: this.profesor.nombre,
        username: this.profesor.username,
        rol: this.profesor.rol
      };
  
      let existe = this.profesores.some(p => p.professor_cod === this.profesor.professor_cod);
  
      if (existe) {
        this.profesorService.updateProfesores(simpleProfesor).subscribe(
          () => {
            this.getProfesores();
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Profesor Updated', life: 3000 });
          },
          (error) => {
            const errorMessage = error?.error?.message || error.statusText || 'An error occurred';
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to update profesor: ' + errorMessage, life: 3000 });
          }
        );
      } else {
        this.profesorService.addProfesores(simpleProfesor).subscribe(
          () => {
            this.getProfesores();
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Profesor Created', life: 3000 });
          },
          (error) => {
            const errorMessage = error?.error?.message || error.statusText || 'An error occurred';
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Failed to create profesor: ' + errorMessage, life: 3000 });
          }
        );
      }
  
      this.profesorDialog = false;
      this.profesor = new User();
    }
  }
}