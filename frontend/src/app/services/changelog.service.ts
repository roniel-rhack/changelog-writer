import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Changelog} from "../changelog-display/changelog.interface";

@Injectable({
  providedIn: 'root'
})
export class ChangelogService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  loadChangelog(): Observable<Changelog> {
    return this.http.get<Changelog>(`${this.apiUrl}/changelog`);
  }

  addEntry(entry: string): Observable<any> {
    const url = `${this.apiUrl}/changelog/add`;
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    headers.append('Access-Control-Allow-Origin', '*');
    const requestBody = {entry};
    return this.http.post(url, requestBody, {headers});
  }

  addVersion(versionData: any) {
    const url = `${this.apiUrl}/changelog`;
    return this.http.post(url, versionData);
  }
}
