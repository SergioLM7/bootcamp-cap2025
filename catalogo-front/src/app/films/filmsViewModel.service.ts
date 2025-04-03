import {
  HttpClient,
  HttpContextToken,
  HttpErrorResponse,
} from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment.development';
import { NotificationService } from '../common-services';
import { LoggerService } from '@my/core';


export type ModeCRUD = 'list' | 'add' | 'edit' | 'view' | 'delete';

export const AUTH_REQUIRED = new HttpContextToken<boolean>(() => false);

export abstract class RESTDAOService<T, K> {
  protected baseUrl = environment.apiUrl;
  protected http: HttpClient = inject(HttpClient);
  constructor(entity: string, protected option = {}) {
    this.baseUrl += entity;
  }
  query(): Observable<T> {
    return this.http.get<T>(this.baseUrl, this.option);
  }
  get(id: K): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}/${id}`, this.option);
  }
  add(item: T): Observable<T> {
    return this.http.post<T>(this.baseUrl, item, this.option);
  }
  change(id: K, item: T): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}/${id}`, item, this.option);
  }
  remove(id: K): Observable<T> {
    return this.http.delete<T>(`${this.baseUrl}/${id}`, this.option);
  }
}

@Injectable({
  providedIn: 'root',
})
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export class FilmsDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('film/v1');
  }
}

@Injectable({
  providedIn: 'root',
})
export class FilmsViewModelService {
  protected mode: ModeCRUD = 'list';
  protected listArray: any[] = [];
  protected element: any = {};
  protected idOriginal: any = null;
  protected listURL = '/films';

  constructor(
    protected notify: NotificationService,
    protected out: LoggerService,
    protected dao: FilmsDAOService,
    protected router: Router
  ) {}

  public get Mode(): ModeCRUD {
    return this.mode;
  }
  public get List(): any[] {
    return this.listArray;
  }
  public get Element(): any {
    return this.element;
  }

  public list(): void {
    this.dao.query().subscribe({
      next: (data) => {
        this.listArray = data;
        this.mode = 'list';
      },
      error: (err) => this.handleError(err),
    });
  }

  public add(): void {
    this.element = {};
    this.mode = 'add';
  }

  public edit(key: any): void {
    this.dao.get(key).subscribe({
      next: (data) => {
        this.element = data;
        this.idOriginal = key;
        console.log(this.element)
        this.mode = 'edit';
      },
      error: (err) => this.handleError(err),
    });
  }

  public view(key: any): void {
    this.dao.get(key).subscribe({
      next: (data) => {
        this.element = data;
        this.mode = 'view';
      },
      error: (err) => this.handleError(err),
    });
  }

  public delete(key: any): void {
    if (!window.confirm('¿Seguro?')) {
      return;
    }
    this.dao.remove(key).subscribe({
      next: () => this.list(),
      error: (err) => this.handleError(err),
    });
  }

  public cancel(): void {
    this.element = {};
    this.idOriginal = null;
    this.clear();
    // this.list();
    this.router.navigateByUrl(this.listURL);   
  }

  public send(): void {
    switch (this.mode) {
      case 'add':
        this.dao.add(this.element).subscribe({
          next: () => this.cancel(),
          error: (err) => this.handleError(err),
        });
        break;
      case 'edit':
        this.dao.change(this.idOriginal, this.element).subscribe({
          next: () => this.cancel(),
          error: (err) => this.handleError(err),
        });
        break;
      case 'view':
        this.cancel();
        break;
    }
  }

  clear() {
    this.element = {};
    this.idOriginal = undefined;
    this.listArray = [];
  }

  handleError(err: HttpErrorResponse) {
    let msg = '';
    switch (err.status) {
      case 0:
        msg = err.message;
        break;
      case 404:
        this.router.navigateByUrl('/404.html'); 
        return;
      default:
        msg = `ERROR ${err.status}: ${err.error?.['title'] ?? err.statusText}.${
          err.error?.['detail'] ? ` Detalles: ${err.error['detail']}` : ''
        }`;
        break;
    }
    this.notify.add(msg);
  }
}
