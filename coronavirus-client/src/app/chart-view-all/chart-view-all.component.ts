import { Component, OnInit } from '@angular/core';
import { CoronaDataService } from '../corona-data.service';
import * as FusionCharts from 'fusioncharts';
import * as charts from 'fusioncharts/fusioncharts.charts';
import * as FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";

@Component({
  selector: 'app-chart-view-all',
  templateUrl: './chart-view-all.component.html',
  styleUrls: ['./chart-view-all.component.css'],
  template:'<div style="text-align: center;" > <fusioncharts [width]="width" [height]="height" [type]="type" [dataFormat]="dataFormat" [dataSource]="chartSource"></fusioncharts></div>'
})

export class ChartViewAllComponent implements OnInit  {
  timeSeriesData: any;
  timeDeathSeriesData:any;
  timeSeriesRecoveredData:any;
  chartSource: any;
  width = 600;
  height = 400;
  type = "msline";
  dataFormat = "json";
  dataSource : any;
  i=0;
  categoriesList: Category[]=[];
  dataConfirmedList: CategoryValues[]=[];
  dataDeathList: CategoryValues[]=[];
  dataRecoveredList: CategoryValues[]=[];
  constructor(private service:CoronaDataService) { }
  ngOnInit() {
    let getTimeSeriesResult=this.service.getTimeSeriesResult();
    getTimeSeriesResult.subscribe(data=>{
      this.timeSeriesData=data; 
      let getTimedeathSeriesResult=this.service.getTimeSeriesDeadResult();
      getTimedeathSeriesResult.subscribe(data=>{
        this.timeDeathSeriesData=data;  
        let getTimeRecoveredSeriesResult=this.service.getTimeSeriesRecoveredResult();
        getTimeRecoveredSeriesResult.subscribe(data=>{
          this.timeSeriesRecoveredData=data;                   
          for(let result of this.timeSeriesData){
            this.categoriesList.push(new Category(result.label));
            this.dataConfirmedList.push(new CategoryValues(result.value));
          }
          for(let result of this.timeDeathSeriesData){
            this.dataDeathList.push(new CategoryValues(result.value));
          }
          for(let result of this.timeSeriesRecoveredData){
            this.dataRecoveredList.push(new CategoryValues(result.value));
          }          
          this.chartSource = {
            chart: {
              caption: "Total confirmed,death and recovered cases",
              yaxisname: "Total Number of cases",
              showhovereffect: "1",
              drawcrossline: "1",
              plottooltext: "<b>$dataValue</b> $seriesName",
              theme: "fusion"
            },
            categories: [
              {
                category: this.categoriesList
              }
            ],
            dataset: [
              {
                seriesname: "Total Confirmed",
                data: this.dataConfirmedList,
                color: "#c9a739" 
              },
              {
                seriesname: "Total Deaths",
                data: this.dataDeathList,
                color: "#721c24"
              },
              {
                seriesname: "Total Recovered",
                data: this.dataRecoveredList,
                color: "#155724"
              }
            ]
          };
        });   
       });
      });
    }
  }
  
  export class Category {
    label: string;
    constructor( label:string){
      this.label=label;
    }
  }
  export class CategoryValues {
    value: string;
    constructor( value:string){
      this.value=value;
    }
  }
 