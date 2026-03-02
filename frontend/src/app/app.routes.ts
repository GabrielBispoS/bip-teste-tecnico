import { Routes } from '@angular/router';
import { BeneficioComponent } from './components/beneficios/beneficio.component'; 

export const routes: Routes = [
  { path: '', redirectTo: 'beneficios', pathMatch: 'full' },
  { path: 'beneficios', component: BeneficioComponent }
];