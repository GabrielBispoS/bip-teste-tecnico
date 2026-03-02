import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio, TransferenciaDTO } from '../../models/beneficio.model';

@Component({
  selector: 'app-beneficio',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficio.component.html',
  styleUrls: ['./beneficio.component.css'],
})
export class BeneficioComponent implements OnInit {
  beneficios: Beneficio[] = [];
  transferencia: TransferenciaDTO = { fromId: 0, toId: 0, valor: 0 };
  mensagem: string = '';
  loading: boolean = false;

  constructor(
    private service: BeneficioService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
  this.carregar();

  setTimeout(() => {
    if (this.beneficios.length === 0) {
      this.carregar();
    }
  }, 3000);
}

carregar(): void {
  this.loading = true; // Ativa o loading
  this.service.listarTodos().subscribe({
    next: (data) => {
      this.beneficios = data;
      this.loading = false; // Desativa
      this.cdr.detectChanges();
    },
    error: (err) => {
      this.loading = false;
      console.error('Back ainda não respondeu...', err);
      // Opcional: Tentar novamente em 2 segundos
      setTimeout(() => this.carregar(), 2000);
    }
  });
}

executarTransferencia(): void {
  if (this.transferencia.valor <= 0) {
    this.mensagem = 'Erro: O valor deve ser maior que zero.';
    return;
  }

  this.service.transferir(this.transferencia).subscribe({
    next: (msg) => {
      this.mensagem = '✅ Transferência realizada com sucesso!';
      this.transferencia = { fromId: 0, toId: 0, valor: 0 }; 

      this.cdr.detectChanges();

      setTimeout(() => {
        this.carregar();
        this.cdr.detectChanges();
      }, 300);
    },
    error: (err) => {
      this.mensagem = 'Erro: ' + (err.error || 'Falha na operação');
      this.cdr.detectChanges();
    }
  });
}
}