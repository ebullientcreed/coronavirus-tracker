import { Component, OnInit } from '@angular/core';
import { CoronaDataService } from '../corona-data.service';
@Component({
  selector: 'app-chart-view-dead',
  templateUrl: './chart-view-dead.component.html',
  styleUrls: ['./chart-view-dead.component.css'],
  template: '<fusioncharts width="700" height="400" type="line" [dataSource]="chartSource"></fusioncharts>'
})
export class ChartViewDeadComponent implements OnInit {
  timeSeriesData: any;
  chartSource: any;
  constructor(private service:CoronaDataService) { }
  ngOnInit() {
    let getTimeSeriesResult=this.service.getTimeSeriesDeadResult();
    getTimeSeriesResult.subscribe(data=>{
      this.timeSeriesData=data;
      console.log(this.timeSeriesData);
      this.chartSource = {
        chart: {
          //Set the chart caption
          caption: "Number of Deaths in the last 30 days",
          //Set the chart subcaption
          // subCaption: "In MMbbl = One Million barrels",
          //Set the x-axis name
          xAxisName: "Date",
          //Set the y-axis name
          yAxisName: "Number of cases",
          //numberSuffix: "K",
          //Set the theme for your chart
          theme: "fusion",
          width: "100%",
          height: "100%",
        },
        // Chart Data - from step 2
        data: this.timeSeriesData
      };
    });   
  }
}
