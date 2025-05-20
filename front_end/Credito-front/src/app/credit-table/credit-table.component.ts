import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core'; // Adicione Input e OnChanges
import { CommonModule } from '@angular/common';

export interface CreditItem { 
  numeroCredito: string;
  numeroNfse: string;
  dataConstituicao: string;
  valorIssqn: number;
  tipoCredito: string;
  simplesNacional: boolean;
  aliquota: number;
  valorFaturado: number;
  valorDeducao: number;
  baseCalculo: number;
}

@Component({
  selector: 'app-credit-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './credit-table.component.html',
  styleUrls: ['./credit-table.component.scss']
})
export class CreditTableComponent implements OnInit, OnChanges { 
  @Input() creditData: CreditItem[] = [];

  constructor() { }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['creditData']) {
      console.log('Dados da tabela atualizados:', this.creditData);
    }
  }
}