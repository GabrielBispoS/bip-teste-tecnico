import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BeneficioComponent } from './components/beneficios/beneficio.component'; 

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, BeneficioComponent],
  templateUrl: './app.html'
})
export class App {
  title = 'frontend';
}