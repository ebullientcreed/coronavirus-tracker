import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoronaDataService {

  constructor(private http:HttpClient) { }
  public getData(){
    return this.http.get("http://localhost:8080/getData");
  }
  public getCountry(){
    return this.http.get("http://localhost:8080/getCountryData");
  }
  public getDeathData(){
    return this.http.get("http://localhost:8080/getDeathData");
  }
  public getCountryDeathData(){
    return this.http.get("http://localhost:8080/getCountryDeathData");
  }
  public getRecoveredData(){
    return this.http.get("http://localhost:8080/getRecoveryData");
  }
  public getCountryRecoveredData(){
    return this.http.get("http://localhost:8080/getCountryRecoveredData");
  }
  public getResult(){
    return this.http.get("http://localhost:8080/getResult");
  }
  public getTimeSeriesResult(){
    return this.http.get("http://localhost:8080/getTimeConfirmedCases");
  }
  public getTimeSeriesDeadResult(){
    return this.http.get("http://localhost:8080/getTimeDeathCases");
  }
  public getTimeSeriesRecoveredResult(){
    return this.http.get("http://localhost:8080/getTimeRecoveredCases");
  }
}
