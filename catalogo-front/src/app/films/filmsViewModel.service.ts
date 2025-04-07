import {
  HttpContextToken,
  HttpErrorResponse,
} from '@angular/common/http';
import {  Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService } from '../common-services';
import { LoggerService } from '@my/core';
import { RESTDAOService } from '../common-classes/restDAOService.service';
import { Observable } from 'rxjs';


export type ModeCRUD = 'list' | 'add' | 'edit' | 'view' | 'delete';

export const AUTH_REQUIRED = new HttpContextToken<boolean>(() => false);

@Injectable({
  providedIn: 'root',
})
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export class FilmsDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('film/v1');
  }

    override change(id: any, item: any): Observable<any> {
      const url = `${this.baseUrl}/${id}`;
      return this.http.put<any>(url, item, this.option); 
    }

    override add(item: any): Observable<any> {
      return this.http.post<any>(`${this.baseUrl}/create`, item, this.option);
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
  protected totalRecords: number = 0;
  protected page: number = 0;
  protected size: number = 10;

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

  public set Element(element:any) {
    this.element = element;
  }

  public get TotalRecords(): any {
    return this.totalRecords;
  }

  getFilmById(id: number): Observable<any> {
    return this.dao.get(id);
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

  public listPaginated(): void {
    this.dao.queryPaginated(this.page, this.size).subscribe({
      next: (data) => {
        this.listArray = data.content;
        this.totalRecords = data.totalElements;
        this.mode = 'list';
      },
      error: (err) => this.handleError(err),
    });
  }

  public paginate(event: any): void {
    this.page = event.page;
    this.size = event.rows;
    this.listPaginated();
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
    if (!window.confirm('Are you sure?')) {
      return;
    }
    this.dao.remove(key).subscribe({
      next: () => {
        this.list()
        alert('Film deleted correctly');
      },
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
          next: () => {
            this.cancel()
            alert('Film created correctly');
          },
          error: (err) => this.handleError(err),
        });
        break;
      case 'edit':
        this.dao.change(this.idOriginal, this.element).subscribe({
          next: () => {
            this.cancel()
            alert('Film updated correctly');
          },
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
