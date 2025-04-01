import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-formularios',
  imports: [FormsModule],
  templateUrl: './formularios.component.html',
  styleUrl: './formularios.component.css'
})
export class FormulariosComponent {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public element: any = {};

  public mode: 'add' | 'edit' = 'add';

  add() {
    this.element = {};
    this.mode = 'add';
  }

  edit(key: number) {
    this.element = {id: key, name: "Juan", lastName: "García", email: 'jgarcia@hotmail.es', entryDate: '2025-04-01', age: 30, nif: '45656576A', active: true};
    this.mode = 'edit';
  }

  cancel() {
    this.element= {};
  }

  send() {

    switch(this.mode) {
      case 'add':
        alert(`POST ${JSON.stringify(this.element)}`);
        this.cancel();
        break;
      case 'edit':
        alert(`PUT ${JSON.stringify(this.element)}`);
        this.cancel();
        break;
    }
  }

}
