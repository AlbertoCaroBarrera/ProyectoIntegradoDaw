import { Component, OnInit } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AusenciaService } from '../../services/ausencia/ausencia.service';
import { UserService } from '../../services/user/user.service';
import { AusenciaDetalle } from '../../clases/ausenciaDetalle';
import { User } from '../../clases/user';
import { Ausencia } from '../../clases/ausencia';
import { CalendarModule } from 'primeng/calendar';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { InputTextModule } from 'primeng/inputtext';
import { CommonModule } from '@angular/common';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-ausencia',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CalendarModule, DialogModule, ButtonModule, TableModule, InputTextModule, CommonModule, ConfirmDialogModule, ToastModule],
  templateUrl: './ausencia.component.html',
  styleUrls: ['./ausencia.component.css'],
  providers:[MessageService]
})
export class AusenciaComponent implements OnInit {
  cols!: any[];
  filas: AusenciaDetalle[] = [];
  ausencias!: Ausencia[];
  fecha: Date = new Date();  // Inicializar con la fecha actual
  profesoresGuardia: string[] = [];
  profesoresGuardiaTable: string[] = [];
  displayDialogCrear: boolean = false;
  displayDialogEliminar: boolean = false;
  profesorConectado!: User | null;

  nuevaAusencia = {
    id: null,
    profesor: {
      professor_cod: ''
    },
    fechaAusencia: new Date(),
    comentario: ''
  };

  constructor(
    private ausenciaService: AusenciaService,
    private userService: UserService,
    private router: Router,
    private messageService: MessageService
  ) { }

  ngOnInit() {
    this.cols = [
      { field: 'nombreProfesor', header: 'Profesor Nombre' },
      { field: 'comentario', header: 'Comentario' },
      { field: 'aulas', header: 'Aulas' },
      { field: 'grupos', header: 'Grupos' },
      { field: 'asignaturas', header: 'Asignaturas' }
    ];
  
    this.userService.getUser().subscribe(user => {
      this.profesorConectado = user;
      if (this.profesorConectado) {
        this.nuevaAusencia.profesor.professor_cod = this.profesorConectado.professor_cod;
      }
      this.loadProfesoresGuardia();
      this.onSubmit();  // Carga inicial de los datos de ausencias
    });
  }
  
  onSubmit() {
    const formattedDate = this.formatDate(this.fecha);
    if (!formattedDate) {
      console.error('Fecha no válida para la solicitud.');
      return;
    }
    this.ausenciaService.getAusenciasDetalles(formattedDate).subscribe(data => {
      if (data.length === 0 || data.every(d => d.nombreProfesor === null)) {
        this.filas = [{id:0, nombreProfesor: 'No hay profesores ausentes hoy', comentario: '', aulas: [], grupos: [], asignaturas: [],nombresProfesoresGuardia:[] }];
      } else {
        this.filas = data.filter(d => d.nombreProfesor !== null).map(fila => ({
          ...fila,
          aulas: fila.aulas ? fila.aulas : [],
          grupos: fila.grupos ? fila.grupos : [], 
          asignaturas: fila.asignaturas ? fila.asignaturas : []
        }));
      }
      this.profesoresGuardiaTable = data.length > 0 ? data[0].nombresProfesoresGuardia : [];
    }, error => {
      console.error('Error al obtener las ausencias:', error);
    });
  }
  
  loadProfesoresGuardia() {
    const formattedDate = this.formatDate(this.fecha);
    if (!formattedDate) {
      console.error('Fecha no válida para la solicitud.');
      return;
    }
    this.ausenciaService.getAusenciasDetalles(formattedDate).subscribe(data => {
      this.profesoresGuardiaTable = data.length > 0 ? data[0].nombresProfesoresGuardia : [];
    }, error => {
      console.error('Error al obtener los profesores de guardia:', error);
    });
  }
  
  onFechaChange(date: Date) {
    this.fecha = date;
    this.onSubmit();
    this.loadProfesoresGuardia();
  }

  onCrearSubmit() {
    if (this.profesorConectado) {
      this.nuevaAusencia.profesor.professor_cod = this.profesorConectado.professor_cod;
      this.ausenciaService.addAusencias(this.nuevaAusencia).subscribe(response => {
        this.displayDialogCrear = false;
        this.onSubmit();
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Ausencia creada', life: 3000 });
      }, error => {
        console.error('Error al crear la ausencia:', error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error al crear la ausencia', life: 3000 });
      });
    }
  }

  navigateToEliminar() {
    this.router.navigate(['/ausencia-eliminar']);
  }

  generatePdf() {
    const doc = new jsPDF();
    const formattedDate = this.formatDate(this.fecha);

    doc.text('Reporte de Ausencias', 14, 20);
    doc.text(`Fecha: ${formattedDate}`, 14, 30);

    if (!this.filas || this.filas.length === 0) {
      console.error('No data available for generating PDF.');
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No hay datos de ausencias para generar el PDF', life: 3000 });
      return;
    }

    (doc as any).autoTable({
      head: [['Profesor Nombre', 'Comentario', 'Aulas', 'Grupos', 'Asignaturas']],
      body: this.filas.map(fila => [
        fila.nombreProfesor,
        fila.comentario,
        fila.aulas.join(', '),
        fila.grupos.join(', '),
        fila.asignaturas.join(', ')
      ]),
      startY: 40,
      styles: {
        fontSize: 10
      },
      headStyles: {
        fillColor: [22, 160, 133]
      }
    });

    let finalY = (doc as any).lastAutoTable.finalY;

    if (finalY > 0) {
      finalY += 10;
    }

    doc.text('Profesores de Guardia', 14, finalY + 10);

    if (!this.profesoresGuardiaTable || this.profesoresGuardiaTable.length === 0) {
      console.error('No guardia data available for generating PDF.');
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'No hay datos de profesores de guardia para generar el PDF', life: 3000 });
      return;
    }

    (doc as any).autoTable({
      head: [['Profesores']],
      body: this.profesoresGuardiaTable.map(profesor => [profesor]),
      startY: finalY + 20,
      styles: {
        fontSize: 10
      },
      headStyles: {
        fillColor: [22, 160, 133]
      }
    });

    doc.save('reporte-ausencias.pdf');
  }

  formatDate(date: Date): string {
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
