import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BeneficioService } from '../../services/beneficio.service';
import { Beneficio, TransferenciaDTO } from '../../models/beneficio.model';

@Component({
  selector: 'app-beneficio',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './beneficio.component.html',
  styleUrls: ['./beneficio.component.css']
})
export class BeneficioComponent implements OnInit {
  beneficios: Beneficio[] = [];
  transferencia: TransferenciaDTO = { fromId: 0, toId: 0, amount: 0 };
  mensagem: string = '';

  constructor(private service: BeneficioService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.service.listarTodos().subscribe(data => this.beneficios = data);
  }

  executarTransferencia(): void {
    this.service.transferir(this.transferencia).subscribe({
      next: (msg) => {
        this.mensagem = msg;
        this.carregar(); // Recarrega saldos após sucesso
      },
      error: (err) => this.mensagem = 'Erro: ' + (err.error || 'Falha na operação')
    });
  }
}