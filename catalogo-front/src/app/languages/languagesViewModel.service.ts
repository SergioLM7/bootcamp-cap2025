import {
  HttpContextToken,
  HttpErrorResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
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
export class LanguagesDAOService extends RESTDAOService<any, any> {
  constructor() {
    super('language/v1');
  }
}

@Injectable({
  providedIn: 'root',
})
export class LanguagesViewModelService {
  protected mode: ModeCRUD = 'list';
  protected listArray: any[] = [];
  protected element: any = {};
  protected idOriginal: any = null;
  protected listURL = '/languages';

  constructor(
    protected notify: NotificationService,
    protected out: LoggerService,
    protected dao: LanguagesDAOService,
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

    public getAllLanguages(): Observable<any[]> {
      return this.dao.query(); 
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
        this.list();
        alert('Language deleted correctly');
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
            this.cancel();
            alert('Language created correctly');
          },
          error: (err) => this.handleError(err),
        });
        break;
      case 'edit':
        this.dao.change(this.idOriginal, this.element).subscribe({
          next: () => {
            this.cancel()
            alert('Language updated correctly');
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
