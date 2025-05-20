// src/app/search-bar/search-bar.component.ts
import { Component, EventEmitter, Output, OnInit } from '@angular/core'; // Adicione OnInit
import { ReactiveFormsModule, FormGroup, FormControl } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-search-bar',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})
export class SearchBarComponent implements OnInit {
  searchForm: FormGroup = new FormGroup({
    searchTerm: new FormControl(''),
    searchType: new FormControl('numeroNfse')
  });

  @Output() search = new EventEmitter<{ term: string, type: string }>();

  constructor() { }

  ngOnInit(): void {
    this.searchForm.get('searchType')?.valueChanges.subscribe(type => {
      console.log('Tipo de busca selecionado:', type);
    });
  }

  onSearch(): void {
    this.search.emit({
      term: this.searchForm.value.searchTerm,
      type: this.searchForm.value.searchType
    });
    console.log('Termo de busca:', this.searchForm.value.searchTerm, 'Tipo:', this.searchForm.value.searchType);
  }
}