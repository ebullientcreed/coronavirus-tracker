import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CoronaDataService {

  constructor(private http:HttpClient) { }
  public getData(){
    return this.http.get("http://localhost:8080/confirmedData");
  }
  public getCountry(){
    return this.http.get("http://localhost:8080/countryData");
  }
  public getDeathData(){
    return this.http.get("http://localhost:8080/deathData");
  }
  public getCountryDeathData(){
    return this.http.get("http://localhost:8080/countryDeathData");
  }
  public getRecoveredData(){
    return this.http.get("http://localhost:8080/recoveryData");
  }
  public getCountryRecoveredData(){
    return this.http.get("http://localhost:8080/countryRecoveredData");
  }
  public getResult(){
    return this.http.get("http://localhost:8080/tesult");
  }
  public getTimeSeriesResult(){
    return this.http.get("http://localhost:8080/timeConfirmedCases");
  }
  public getTimeSeriesDeadResult(){
    return this.http.get("http://localhost:8080/timeDeathCases");
  }
  public getTimeSeriesRecoveredResult(){
    return this.http.get("http://localhost:8080/timeRecoveredCases");
  }
}
