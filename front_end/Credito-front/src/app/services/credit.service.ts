// src/app/services/credit.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CreditItem } from '../credit-table/credit-table.component'; // Importa a interface

@Injectable({
  providedIn: 'root' 
})
export class CreditService {
  // URL base da sua API (ajuste conforme necessário)
  private baseUrl = '/api/creditos'; // Certifique-se de que o proxy do Angular está configurado, ou use a URL completa

  constructor(private http: HttpClient) { }

  /**
   * Busca créditos pelo número da NFSE.
   * @param numeroNfse O número da NFSE para buscar.
   * @returns Um Observable de CreditItem[] (ou um array vazio se não houver dados).
   */
  getCreditsByNfse(numeroNfse: string): Observable<CreditItem[]> {
    const url = `${this.baseUrl}/${numeroNfse}`;
    console.log(`Buscando por NFSE: ${url}`);
    return this.http.get<CreditItem[]>(url).pipe(
      catchError(this.handleError) // Lida com erros HTTP
    );
  }

  /**
   * Busca créditos pelo número do Crédito.
   * @param numeroCredito O número do Crédito para buscar.
   * @returns Um Observable de CreditItem[] (ou um array vazio se não houver dados).
   */
  getCreditsByCreditNumber(numeroCredito: string): Observable<CreditItem[]> {
    const url = `${this.baseUrl}/credito/${numeroCredito}`;
    console.log(`Buscando por Número de Crédito: ${url}`);
    return this.http.get<CreditItem[]>(url).pipe(
      catchError(this.handleError) 
    );
  }

  /**
   * Lida com erros HTTP.
   * @param error O objeto HttpErrorResponse.
   * @returns Um Observable com um erro que pode ser tratado pelo consumidor.
   */
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Ocorreu um erro desconhecido!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      errorMessage = `Código do erro: ${error.status}\nMensagem: ${error.message}`;
      if (error.status === 404) {
        errorMessage = 'Nenhum crédito encontrado para o termo de busca.';
      }
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage)); 
  }
}