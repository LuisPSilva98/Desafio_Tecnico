// src/app/app.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { SearchBarComponent } from './search-bar/search-bar.component';
import { CreditTableComponent, CreditItem } from './credit-table/credit-table.component';
import { CreditService } from './services/credit.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    SearchBarComponent,
    CreditTableComponent
  ],
  template: `
    <header class="app-header">
      <h1>Sistema de Gerenciamento de Créditos</h1>
    </header>
    <main>
      <app-search-bar (search)="handleSearch($event)"></app-search-bar>
      <app-credit-table [creditData]="(filteredCreditData$ | async) || []"></app-credit-table>
      <div *ngIf="errorMessage" class="error-message">{{ errorMessage }}</div>
      <div *ngIf="!(filteredCreditData$ | async)?.length && !errorMessage && searchAttempted" class="no-results-message">
        Nenhum crédito encontrado para o termo fornecido.
      </div>
    </main>
  `,
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'credit-app';
  filteredCreditData$: Observable<CreditItem[]> = of([]);
  errorMessage: string | null = null;
  searchAttempted: boolean = false;

  constructor(private creditService: CreditService) { }

  ngOnInit(): void {
    this.filteredCreditData$ = of([]);
    this.searchAttempted = false; 
  }

  
  handleSearch(searchData: { term: string, type: string }): void {
    this.errorMessage = null; 
    this.searchAttempted = true; 

    const term = searchData.term.trim(); 

    if (!term) {
      this.filteredCreditData$ = of([]); 
      this.errorMessage = 'Por favor, digite um número para buscar.';
      return;
    }

    
    if (!/^\d+$/.test(term)) { 
        this.errorMessage = 'O termo de busca deve conter apenas números.';
        this.filteredCreditData$ = of([]);
        return;
    }


    let requestObservable: Observable<CreditItem[]>;

    if (searchData.type === 'numeroNfse') {
      requestObservable = this.creditService.getCreditsByNfse(term);
    } else if (searchData.type === 'numeroCredito') {
      requestObservable = this.creditService.getCreditsByCreditNumber(term);
    } else {
      this.errorMessage = 'Tipo de busca inválido selecionado.';
      this.filteredCreditData$ = of([]);
      return;
    }

    this.filteredCreditData$ = requestObservable.pipe(
      catchError(error => {
        this.errorMessage = error.message || 'Erro ao buscar dados. Tente novamente mais tarde.';
        console.error('Erro ao buscar dados no componente:', error);
        return of([]);
      })
    );
  }
}