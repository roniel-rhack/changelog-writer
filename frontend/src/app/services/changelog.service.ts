import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChangelogService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  loadChangelog(): Observable<string> {
    return this.http.get(`${this.apiUrl}/changelog`,{ responseType: 'text' });
  }

  addEntry(entry: string): Observable<any> {
    const url = `${this.apiUrl}/changelog/add`;
    // eslint-disable-next-line @typescript-eslint/naming-convention
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
