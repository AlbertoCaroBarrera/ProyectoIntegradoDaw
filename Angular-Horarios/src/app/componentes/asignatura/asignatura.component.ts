import { Component, ViewChild } from '@angular/core';
import { AsignaturaService } from '../../services/asignatura/asignatura.service';
import { PrimengModule } from '../../primeng.module';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Asignatura } from '../../clases/asignatura';

interface Column {
  field: string;
  header: string;
}

@Component({
  selector: 'app-asignatura',
  standalone: true,
  imports: [PrimengModule,FormsModule],
  templateUrl: './asignatura.component.html',
  styleUrl: './asignatura.component.css',
  providers: [MessageService,ConfirmationService]
})
export class AsignaturaComponent {
  asignaturas!: Asignatura[];
  selectedAsignaturas!: Asignatura[] | null ;
  asignatura!: Asignatura;
  asignaturaDialog: boolean = false;
  submitted: boolean = false;

  constructor(private asignaturaSevice: AsignaturaService, private messageService: MessageService, private confirmationService: ConfirmationService){}

  ngOnInit():void {
    this.getAsignaturas();
  }

  getAsignaturas()
  {
    this.asignaturaSevice.getAsignaturas().subscribe(data=> this.asignaturas = data)
  }

  openNew() {
    this.asignatura = {

      "asignaturaCod":"",
      "descripcion":""
    };
    this.submitted = false;
    this.asignaturaDialog = true;
}


  editAsignatura(asignatura: Asignatura) {
    this.asignatura = { ...asignatura };
    this.asignaturaDialog = true;
  }

  deleteAsignatura(asignatura: Asignatura) {
    this.confirmationService.confirm({
        message: 'Are you sure you want to delete ' + asignatura.descripcion + '?',
        header: 'Confirm',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.asignaturaSevice.deleteAsignatura(asignatura.asignaturaCod).subscribe(()=>{
              this.getAsignaturas();
            }
            )
            this.asignaturas = this.asignaturas.filter((val) => val.asignaturaCod !== asignatura.asignaturaCod);
            this.asignatura = {

              "asignaturaCod":"",
              "descripcion":""
            };
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Asignatura Borrada', life: 3000 });
        }
    });
  }

  hideDialog() {
    this.asignaturaDialog = false;
    this.submitted = false;
  }

  saveAsignatura() {
    this.submitted = true;
  
    if (this.asignatura.descripcion?.trim()) {
        const asignaturaExists = this.asignaturas.some(existingAsignatura => existingAsignatura.asignaturaCod === this.asignatura.asignaturaCod);
  
        if (asignaturaExists) {
            this.asignaturaSevice.updateAsignaturas(this.asignatura).subscribe(() => {
                this.getAsignaturas();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Asignatura Actualizada', life: 3000 });
            });
        } else {
            this.asignaturaSevice.addAsignaturas(this.asignatura).subscribe(() => {
                this.getAsignaturas();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Asignatura Creada', life: 3000 });
            });
        }
  
        this.asignaturas = [...this.asignaturas];
        this.asignaturaDialog = false;
        this.asignatura = {
          "asignaturaCod": "",
          "descripcion": ""
        };
    }
  }

  findIndexById(id: String): number {
    let index = -1;
    for (let i = 0; i < this.asignaturas.length; i++) {
        if (this.asignaturas[i].asignaturaCod == id) {
            index = i;
            break;
        }
    }

    return index;
  }

  
}

