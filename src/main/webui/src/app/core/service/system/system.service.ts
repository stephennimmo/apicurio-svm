import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SystemService {

  constructor(private readonly httpClient: HttpClient) {
  }

  findAll(): Observable<System[]> {
    return this.httpClient.get<System[]>("/api/systems");
  }

}

export interface System {
  systemId: number;
  name: string;
}
