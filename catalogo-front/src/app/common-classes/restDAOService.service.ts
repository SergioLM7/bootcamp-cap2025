import { HttpClient } from "@angular/common/http";
import { inject } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment.development";

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
      return this.http.put<T>(this.baseUrl, item, this.option);
    }
    remove(id: K): Observable<T> {
      return this.http.delete<T>(`${this.baseUrl}/${id}`, this.option);
    }
  }