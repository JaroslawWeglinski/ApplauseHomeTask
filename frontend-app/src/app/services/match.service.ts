import {ApiService} from "./api.service";
import {Observable} from "rxjs";
import {Device} from "../models/device";
import {Tester} from "../models/tester";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  constructor(private apiService: ApiService) {
  }

  public getDevices(): Observable<Device[]> {
    return this.apiService.get('devices','');
  }

  public getCountries(): Observable<string[]> {
    return this.apiService.get('countries','');
  }

  public match(countries: string[], devices: number[]): Observable<Tester[]> {
    return this.apiService.post('testers', {countries, devices});
  }


}
