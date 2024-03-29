import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-seating',
  templateUrl: './seating.component.html',
  styleUrls: ['./seating.component.css']
})
export class SeatingComponent implements OnInit{
  
  rows: number = 9;
  columns: number = 9; 

  selectedSeats: any[] = []; 
  takenSeats: any[] = [];
  numPeople: number = 0;

  constructor() { }

  ngOnInit() {
    this.generateTakenSeats(20);
  }


  toggleSeat(row: number, column: number) {
    const seat = { row, column };
    const index = this.selectedSeats.findIndex(s => s.row === row && s.column === column);
    if (index !== -1) {

      this.selectedSeats.splice(index, 1);
    } else if (this.selectedSeats.length < 5 && !this.isSeatTaken(row, column)) {

      this.selectedSeats.push(seat);
    }
  }
 
  isSeatSelected(row: number, column: number) {
    return this.selectedSeats.some(s => s.row === row && s.column === column);
  }

  getLabel(row: number, column: number): string {
    const alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    return alphabet.charAt(column) + (row + 1);
  }
  
  getRows(): number[] {
    return Array(this.rows).fill(0).map((_, index) => index);
  }


  getColumns(): number[] {
    return Array(this.columns).fill(0).map((_, index) => index);
  }


  generateTakenSeats(count: number) {
    this.takenSeats = []; 
    for (let i = 0; i < count; i++) {
      const row = Math.floor(Math.random() * this.rows);
      const column = Math.floor(Math.random() * this.columns);
      const seat = { row, column };
      this.takenSeats.push(seat);
    }
  }

  isSeatTaken(row: number, column: number) {
    return this.takenSeats.some(s => s.row === row && s.column === column);
  }

  validateInput(event: any) {
    var input = event.target.value;
    if (!/^\d+$/.test(input) || parseInt(input) < 1 || parseInt(input) > 5) {
      event.target.value = '';
    } else {
      this.selectedSeats = [];
      this.numPeople = parseInt(input);
      this.recommendSeats();
    }
  }

  recommendSeats() {
    // TODO
  }

}
