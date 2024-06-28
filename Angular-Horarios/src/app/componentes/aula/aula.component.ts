import { Component } from '@angular/core';

import { AulaService } from '../../services/aula/aula.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PrimengModule } from '../../primeng.module';
import { FormsModule } from '@angular/forms';
import { Aula } from '../../clases/aula';

interface Column {
  field: string;
  header: string;
}

@Component({
  selector: 'app-aula',
  standalone: true,
  imports: [PrimengModule,FormsModule],
  templateUrl: './aula.component.html',
  styleUrl: './aula.component.css',
  providers: [MessageService,ConfirmationService]
})
export class AulaComponent {
  aulas!: Aula[];
  selectedAulas!: Aula[] | null ;
  aula!: Aula;
  aulaDialog: boolean = false;
  submitted: boolean = false;

  constructor(private aulaSevice: AulaService, private messageService: MessageService, private confirmationService: ConfirmationService){}

  ngOnInit():void {
    this.getAulas();
  }

  getAulas()
  {
    this.aulaSevice.getAulas().subscribe(data=> this.aulas = data)
  }

  openNew() {
    this.aula = {
      "aulaCod":"",
      "descripcion":"",
      
    };
    this.submitted = false;
    this.aulaDialog = true;
}

  deleteSelectedAulas() {
    this.confirmationService.confirm({
        message: 'Are you sure you want to delete the selected aulas?',
        header: 'Confirm',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.aulas = this.aulas.filter((val) => !this.selectedAulas?.includes(val));
            this.selectedAulas = null;
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Aulas Deleted', life: 3000 });
        }
    });
}

  editAula(aula: Aula) {
    this.aula = { ...aula };
    this.aulaDialog = true;
  }

  deleteAula(aula: Aula) {
    this.confirmationService.confirm({
        message: 'Are you sure you want to delete ' + aula.descripcion + '?',
        header: 'Confirm',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.aulaSevice.deleteAula(aula.aulaCod).subscribe(()=>{
              this.getAulas();
            }
            )
            this.aulas = this.aulas.filter((val) => val.aulaCod !== aula.aulaCod);
            this.aula = {
              "aulaCod":"",
              "descripcion":""
            };
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Aula Borrada', life: 3000 });
        }
    });
  }

  hideDialog() {
    this.aulaDialog = false;
    this.submitted = false;
  }

  saveAula() {
    this.submitted = true;

    if (this.aula.descripcion?.trim()) {
        const aulaExists = this.aulas.some(existingAula => existingAula.aulaCod === this.aula.aulaCod);
  
        if (aulaExists) {
            this.aulaSevice.updateAulas(this.aula).subscribe(() => {
                this.getAulas();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Aula Actualizada', life: 3000 });
            });
        } else {
            this.aulaSevice.addAulas(this.aula).subscribe(() => {
                this.getAulas();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Aula Creada', life: 3000 });
            });
        }
  
        this.aulas = [...this.aulas];
        this.aulaDialog = false;
        this.aula = {
          "aulaCod": "",
          "descripcion": ""
        };
    }
  }

  findIndexById(id: String): number {
    let index = -1;
    for (let i = 0; i < this.aulas.length; i++) {
        if (this.aulas[i].aulaCod == id) {
            index = i;
            break;
        }
    }

    return index;
  }

  
}

