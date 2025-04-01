import { HttpClient } from '@angular/common/http';
import { Component, inject, Injectable } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ErrorMessagePipe, NIFNIEValidator, TypeValidator, UppercaseValidator } from '@my/core';
import { Observable } from 'rxjs';
import { NotificationService } from 'src/app/common-services';
import { environment } from 'src/environments/environment.development';


export abstract class RESTDAOService<T, K> {
  protected baseUrl = environment.apiUrl;
  protected http = inject(HttpClient)

  constructor(entidad: string, protected option = {}) {
    this.baseUrl += entidad;
  }
  query(extras = {}): Observable<T[]> {
    return this.http.get<T[]>(this.baseUrl, Object.assign({}, this.option, extras));
  }
  get(id: K, extras = {}): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}/${id}`, Object.assign({}, this.option, extras));
  }
  add(item: T, extras = {}): Observable<T> {
    return this.http.post<T>(this.baseUrl, item, Object.assign({}, this.option, extras));
  }
  change(id: K, item: T, extras = {}): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}/${id}`, item, Object.assign({}, this.option, extras));
  }
  remove(id: K, extras = {}): Observable<T> {
    return this.http.delete<T>(`${this.baseUrl}/${id}`, Object.assign({}, this.option, extras));
  }
}


@Injectable({providedIn: 'root'})
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export class PersonasDaoService extends RESTDAOService<any, number> {
  constructor() {
    super('personas')
  }
}



@Component({
  selector: 'app-formularios',
  imports: [FormsModule, ErrorMessagePipe, NIFNIEValidator, TypeValidator, UppercaseValidator],
  templateUrl: './formularios.component.html',
  styleUrl: './formularios.component.css'
})
export class FormulariosComponent {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public element: any = {};

  public mode: 'add' | 'edit' = 'add';

  constructor(private dao: PersonasDaoService, private notify: NotificationService) {}


  add() {
    this.element = {};
    this.mode = 'add';
  }

  edit(key: number) {
    // this.element = {id: key, name: "Juan", lastName: "García", email: 'jgarcia@hotmail.es', entryDate: '2025-04-01', age: 30, nif: '45656576A', active: true};
    // this.mode = 'edit';
    this.dao.get(key).subscribe({
      next: data => {
        this.element = data
        this.mode = 'edit'
      },
      error: err => this.notify.add(JSON.stringify(err))
    })
  }

  cancel() {
    this.element= {};
  }

  send() {
    switch(this.mode) {
      case 'add':
         this.dao.add(this.element).subscribe({
          next: () => this.cancel(),
          error: err => this.notify.add(JSON.stringify(err))
        })
        //  alert(`POST ${JSON.stringify(this.elemento)}`)
        // this.cancel()
        break;
      case 'edit':
        this.dao.change(this.element.id, this.element).subscribe({
          next: () => this.cancel(),
          error: err => this.notify.add(JSON.stringify(err))
        })
        // alert(`PUT ${JSON.stringify(this.elemento)}`)
        // this.cancel()
        break;
    }
  }
}
