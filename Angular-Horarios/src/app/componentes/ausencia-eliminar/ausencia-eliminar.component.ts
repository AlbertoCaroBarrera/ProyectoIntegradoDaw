import { Component, OnInit } from '@angular/core';
import { AusenciaService } from '../../services/ausencia/ausencia.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Ausencia } from '../../clases/ausencia';
import { PrimengModule } from '../../primeng.module';
import { UserService } from '../../services/user/user.service';
import { User } from '../../clases/user';
import { CalendarModule } from 'primeng/calendar';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ausencia-eliminar',
  standalone: true,
  imports: [PrimengModule, CalendarModule, FormsModule],
  templateUrl: './ausencia-eliminar.component.html',
  styleUrls: ['./ausencia-eliminar.component.css'],
  providers: [ConfirmationService, MessageService]
})
export class AusenciaEliminarComponent implements OnInit {
  ausencias: Ausencia[] = [];
  profesorConectado!: User | null;
  fechaFiltro!: Date;

  constructor(
    private ausenciaService: AusenciaService,
    private userService: UserService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    this.userService.getUser().subscribe(user => {
      this.profesorConectado = user;
      if (this.profesorConectado) {
        this.loadAusencias();
      }
    });
  }

  loadAusencias() {
    this.ausenciaService.getAusencias().subscribe(data => {
      this.ausencias = data.filter(ausencia => ausencia.profesor.professor_cod === this.profesorConectado!.professor_cod);
    });
  }

  confirmDelete(id: number) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete this absence?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.ausenciaService.deleteAusencia(id).subscribe(() => {
          this.ausencias = this.ausencias.filter(ausencia => ausencia.id !== id);
          this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Ausencia eliminada', life: 3000 });
        }, error => {
          console.error('Error al eliminar la ausencia:', error);
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error al eliminar la ausencia', life: 3000 });
        });
      }
    });
  }

  onFechaChange() {
    this.loadAusencias();
  }

  formatDate(date: Date | undefined): string {
    if (!date) {
      return '';
    }
    const d = new Date(date);
    let month = '' + (d.getMonth() + 1);
    let day = '' + d.getDate();
    const year = d.getFullYear();

    if (month.length < 2) {
      month = '0' + month;
    }
    if (day.length < 2) {
      day = '0' + day;
    }

    return [year, month, day].join('-');
  }
}
