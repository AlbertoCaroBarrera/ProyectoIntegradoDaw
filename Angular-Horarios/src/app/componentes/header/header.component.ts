import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { LoginService } from '../../services/auth/login.service';
import { PrimengModule } from '../../primeng.module';
import { MenuItem, MessageService } from 'primeng/api';
import { User } from '../../clases/user';
import { UserService } from '../../services/user/user.service';
import { UserStateService } from '../../services/user/userState.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-header',
  standalone: true,
  imports: [PrimengModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
  providers: [MessageService]
})
export class HeaderComponent implements OnInit {
  errorMessage: String = "";
  userLoginOn: boolean = false;
  items!: MenuItem[];
  items2: MenuItem[];
  user: User | null = null;

  constructor(
    private loginService: LoginService,
    private userStateService: UserStateService,
    private changeDetector: ChangeDetectorRef,
    private userService: UserService,
    private router: Router
  ) {
    this.items2 = [
      {
        label: 'Configuracion',
        icon: 'pi pi-cog',
        routerLink: '/config'
      },
      {
        label: 'Cerrar sesión',
        icon: 'pi pi-fw pi-sign-out',
        command: () => {
          this.loginService.logout();
          this.router.navigate(['/login']);
        }
      }
    ];
  }

  ngOnInit(): void {
    this.loginService.userLoginOn.subscribe({
      next: (isLoggedIn: boolean) => {
        this.userLoginOn = isLoggedIn;
        if (isLoggedIn) {
          this.userService.fetchAndUpdateUser();
          this.userStateService.user.subscribe({
            next: (userData: User | null) => {
              this.user = userData;
              this.initializeMenuItems();
              this.changeDetector.detectChanges(); 
            },
            error: (error: any) => {
              this.errorMessage = error.message;
            }
          });
        } else {
          this.user = null;
          this.initializeMenuItems();
        }
      },
      error: (error: any) => {
        console.error('Error al verificar el estado de autenticación:', error);
      }
    });
  }

  private initializeMenuItems(): void {
    this.items = [
      {
        label: 'TempoClick',
        icon: 'pi pi-fw pi-home',
        routerLink: '/inicio'
      }
    ];

    if (this.userLoginOn) {
      this.items.push({
        label: 'Profesores',
        icon: 'pi pi-fw pi-user',
        items: [
          {
            label: 'Horarios',
            icon: 'pi pi-calendar-clock',
            routerLink: '/horarios'
          },
          {
            label: 'Ausencia',
            icon: 'pi pi-fw pi-calendar',
            routerLink: '/ausencia'
          },
          {
            label: 'Lista de Profesores',
            icon: 'pi pi-fw pi-id-card',
            routerLink: '/profesores'
          }
        ]
      });
    }

    if (this.user?.rol === 'ADMIN') {
      this.items.push(
        {
          label: 'Aulas',
          icon: 'pi pi-fw pi-building',
          routerLink: '/aulas'
        },
        {
          label: 'Franjas',
          icon: 'pi pi-fw pi-clock',
          routerLink: '/franjas'
        },
        {
          label: 'Grupos',
          icon: 'pi pi-fw pi-users',
          routerLink: '/grupos'
        },
        {
          label: 'Asignaturas',
          icon: 'pi pi-fw pi-table',
          routerLink: '/asignaturas'
        },
        {
          label: 'XML',
          icon: 'pi pi-fw pi-database',
          routerLink: '/xml'
        }
      );
    }
  }
}