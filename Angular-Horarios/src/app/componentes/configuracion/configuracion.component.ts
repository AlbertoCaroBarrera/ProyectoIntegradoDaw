import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { User } from '../../clases/user';
import { UserService } from '../../services/user/user.service';
import { ProfesorService } from '../../services/profesor/profesor.service';
import { PrimengModule } from '../../primeng.module';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-configuracion',
  standalone: true,
  imports: [PrimengModule,FormsModule],
  templateUrl: './configuracion.component.html',
  styleUrl: './configuracion.component.css',
  providers: [MessageService]
})
export class ConfiguracionComponent implements OnInit{
  user: User = new User(); // Inicializar user como una instancia de User

  constructor(
    private userService: UserService,
    private profesorService: ProfesorService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.userService.getUser().subscribe({
      next: (userData) => {
        if (userData) {
          this.user = userData;
        }
      },
      error: (error) => {
        console.error('Error al obtener el usuario:', error);
        this.messageService.add({severity: 'error', summary: 'Error', detail: 'No se pudo obtener la información del usuario.'});
      }
    });
  }

  onSubmit(): void {
    if (this.user) {
      const simpleUser = {
        professor_cod: this.user.professor_cod,
        nombre: this.user.nombre,
        username: this.user.username,
        rol: this.user.rol
      };
  
      this.profesorService.updateProfesores(simpleUser).subscribe({
        next: (updatedUser) => {
          this.messageService.add({severity: 'success', summary: 'Éxito', detail: 'Usuario actualizado correctamente.'});
        },
        error: (error) => {
          console.error('Error al actualizar el usuario:', error);
          this.messageService.add({severity: 'error', summary: 'Error', detail: 'No se pudo actualizar el usuario.'});
        }
      });
    }
  }
}