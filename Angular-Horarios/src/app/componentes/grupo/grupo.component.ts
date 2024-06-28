import { Component } from '@angular/core';
import { PrimengModule } from '../../primeng.module';
import { FormsModule } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Grupo } from '../../clases/grupo';
import { GrupoService } from '../../services/grupo/grupo.service';

@Component({
  selector: 'app-grupo',
  standalone: true,
  imports: [PrimengModule,FormsModule],
  templateUrl: './grupo.component.html',
  styleUrl: './grupo.component.css',
  providers: [MessageService,ConfirmationService]
})
export class GrupoComponent {
  grupos!: Grupo[];
  selectedGrupos!: Grupo[] | null ;
  grupo!: Grupo;
  grupoDialog: boolean = false;
  submitted: boolean = false;

  constructor(private grupoSevice: GrupoService, private messageService: MessageService, private confirmationService: ConfirmationService){}

  ngOnInit():void {
    this.getGrupos();
  }

  getGrupos()
  {
    this.grupoSevice.getGrupos().subscribe(data=> this.grupos = data)
  }

  openNew() {
    this.grupo = {
      "grupoCod":"",
      "descripcion":"",
      
    };
    this.submitted = false;
    this.grupoDialog = true;
}

  deleteSelectedGrupos() {
    this.confirmationService.confirm({
        message: 'Are you sure you want to delete the selected grupos?',
        header: 'Confirm',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.grupos = this.grupos.filter((val) => !this.selectedGrupos?.includes(val));
            this.selectedGrupos = null;
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Grupos Deleted', life: 3000 });
        }
    });
}

  editGrupo(grupo: Grupo) {
    this.grupo = { ...grupo };
    this.grupoDialog = true;
  }

  deleteGrupo(grupo: Grupo) {
    this.confirmationService.confirm({
        message: 'Are you sure you want to delete ' + grupo.descripcion + '?',
        header: 'Confirm',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
            this.grupoSevice.deleteGrupo(grupo.grupoCod).subscribe(()=>{
              this.getGrupos();
            }
            )
            this.grupos = this.grupos.filter((val) => val.grupoCod !== grupo.grupoCod);
            this.grupo = {
              "grupoCod":"",
              "descripcion":"",
            };
            this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Grupo Borrada', life: 3000 });
        }
    });
  }

  hideDialog() {
    this.grupoDialog = false;
    this.submitted = false;
  }

  saveGrupo() {
    this.submitted = true;
  
    if (this.grupo.descripcion?.trim()) {
        const grupoExists = this.grupos.some(existingGrupo => existingGrupo.grupoCod === this.grupo.grupoCod);
  
        if (grupoExists) {
            this.grupoSevice.updateGrupos(this.grupo).subscribe(() => {
                this.getGrupos();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Grupo Actualizado', life: 3000 });
            });
        } else {
            this.grupoSevice.addGrupos(this.grupo).subscribe(() => {
                this.getGrupos();
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Grupo Creado', life: 3000 });
            });
        }
  
        this.grupos = [...this.grupos];
        this.grupoDialog = false;
        this.grupo = {
          "grupoCod": "",
          "descripcion": "",
        };
    }
  }
  

  findIndexById(id: String): number {
    let index = -1;
    for (let i = 0; i < this.grupos.length; i++) {
        if (this.grupos[i].grupoCod == id) {
            index = i;
            break;
        }
    }

    return index;
  }

  
}

