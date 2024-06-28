import { Component } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { PrimengModule } from '../../primeng.module';
import { FormsModule } from '@angular/forms';
import { Franja } from '../../clases/franja';
import { FranjaService } from '../../services/franja/franja.service';

@Component({
  selector: 'app-franja',
  standalone: true,
  imports: [PrimengModule, FormsModule],
  templateUrl: './franja.component.html',
  styleUrl: './franja.component.css',
  providers: [MessageService, ConfirmationService]
})
export class FranjaComponent {
  franjas!: Franja[];
  selectedFranjas!: Franja[] | null;
  franja!: Franja;
  franjaDialog: boolean = false;
  submitted: boolean = false;

  hora() {
    const date = new Date();
    date.setHours(0, 0, 0, 0);
    return date.toTimeString().split(' ')[0];
  }

  constructor(private franjaSevice: FranjaService, private messageService: MessageService, private confirmationService: ConfirmationService) { }

  ngOnInit(): void {
    this.getFranjas();
  }

  getFranjas() {
    this.franjaSevice.getFranjas().subscribe(data => this.franjas = data)
  }


  openNew() {
    this.franja = new Franja;
    this.submitted = false;
    this.franjaDialog = true;
  }

  deleteSelectedFranjas() {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete the selected franjas?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.franjas = this.franjas.filter((val) => !this.selectedFranjas?.includes(val));
        this.selectedFranjas = null;
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Franjas Deleted', life: 3000 });
      }
    });
  }

  editFranja(franja: Franja) {
    this.franja = { ...franja };
    this.franjaDialog = true;
  }

  deleteFranja(franja: Franja) {
    this.confirmationService.confirm({
      message: 'Are you sure you want to delete ' + franja.descripcion + '?',
      header: 'Confirm',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (franja.id) {

          this.franjaSevice.deleteFranja(franja.id).subscribe(() => {
            this.getFranjas();
          }
          )
          this.franjas = this.franjas.filter((val) => val.id !== franja.id);
          this.franja = new Franja;
          this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Franja Borrada', life: 3000 });
        }
      }

    });
  }

  hideDialog() {
    this.franjaDialog = false;
    this.submitted = false;
  }

  saveFranja() {
    this.submitted = true;

    if (this.franja.descripcion?.trim()) {
      if (this.franja.id) {
        this.franjaSevice.updateFranjas(this.franja).subscribe(() => {
          this.getFranjas();
        })
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Franja Cambiada', life: 3000 });
      } else {
        this.franjaSevice.addFranjas(this.franja).subscribe(() => {
          this.getFranjas();
        });
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Franja Creada', life: 3000 });
      }

      this.franjas = [...this.franjas];
      this.franjaDialog = false;
      this.franja = new Franja;
    }
  }

  findIndexById(id: number): number {
    let index = -1;
    for (let i = 0; i < this.franjas.length; i++) {
      if (this.franjas[i].id == id) {
        index = i;
        break;
      }
    }

    return index;
  }


}

