import { Component, OnInit, ViewChild, ViewChildren } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { CoronaDataService } from '../corona-data.service';
import { MatSort } from '@angular/material/sort';
@Component({
  selector: 'app-table-pagination',
  templateUrl: './table-pagination.component.html',
  styleUrls: ['./table-pagination.component.css']
})
export class TablePaginationComponent implements OnInit {
  displayedColumns: string[] = ['country', 'latestTotalCases', 'latestTotalDeathCases', 'latestRecoveredCases'];
  Result:any;
  nameList:any;
  dataList:any;
  dataSource= new MatTableDataSource(this.Result);  
  public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }

  constructor(private service:CoronaDataService) { }
  @ViewChild(MatSort,{static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;  
  
  ngOnInit() {
    
    let result=this.service.getResult();
    result.subscribe(data=>{
      this.Result=data;   
      console.log(this.Result[0]);
      this.dataSource=new MatTableDataSource(this.Result); 
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });     
  }
  
}


export interface dataS {
  number: number;
  country: string;
  latestTotalCases: number;
  latestDeathTotalCases: number;
}

