import { Routes } from '@angular/router';
import { LoginComponent } from './componentes/login/login.component';
import { InicioComponent } from './componentes/inicio/inicio.component';
import { AsignaturaComponent } from './componentes/asignatura/asignatura.component';
import { AulaComponent } from './componentes/aula/aula.component';
import { HorarioComponent } from './componentes/horario/horario.component';
import { ProfesorComponent } from './componentes/profesor/profesor.component';
import { FranjaComponent } from './componentes/franja/franja.component';
import { GrupoComponent } from './componentes/grupo/grupo.component';
import { XMLComponent } from './componentes/xml/xml.component';
import { AuthGuard  } from './auth.guard';
import { AusenciaComponent } from './componentes/ausencia/ausencia.component';
import { AusenciaEliminarComponent } from './componentes/ausencia-eliminar/ausencia-eliminar.component';
import { ForgotPasswordComponent } from './componentes/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './componentes/reset-password/reset-password.component';
import { ConfiguracionComponent } from './componentes/configuracion/configuracion.component';

export const routes: Routes = [
    { path: '', component: InicioComponent, canActivate: [AuthGuard ] },
    { path: 'inicio', component: InicioComponent, canActivate: [AuthGuard ] },
    { path: 'config', component: ConfiguracionComponent, canActivate: [AuthGuard ] },
    { path: 'login', component: LoginComponent, canActivate: [] },
    { path: 'forgot-password', component: ForgotPasswordComponent, canActivate: [] },
    { path: 'reset-password', component: ResetPasswordComponent, canActivate: [] },
    { path: 'asignaturas', component: AsignaturaComponent, canActivate: [AuthGuard ] },
    { path: 'aulas', component: AulaComponent, canActivate: [AuthGuard ] },
    { path: 'horarios', component: HorarioComponent, canActivate: [AuthGuard ] },
    { path: 'profesores', component: ProfesorComponent, canActivate: [AuthGuard ] },
    { path: 'franjas', component: FranjaComponent, canActivate: [AuthGuard ] },
    { path: 'grupos', component: GrupoComponent, canActivate: [AuthGuard ] },
    { path: 'xml', component: XMLComponent, canActivate: [AuthGuard ] },
    { path: 'ausencia', component: AusenciaComponent, canActivate: [AuthGuard ] },
    { path: 'ausencia-eliminar', component: AusenciaEliminarComponent, canActivate: [AuthGuard ] },

];