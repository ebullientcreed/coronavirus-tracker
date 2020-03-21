import { Component, OnInit,ViewChild,
  ViewContainerRef,ComponentFactoryResolver,ComponentRef,
  ComponentFactory} from '@angular/core';
import { CoronaDataService } from '../corona-data.service';
import { faFileMedical,faCheckCircle,faUserSlash,faChartLine } from '@fortawesome/free-solid-svg-icons';
//import { FusionCharts } from "angular-fusioncharts";
import * as FusionCharts from 'fusioncharts';
import * as charts from 'fusioncharts/fusioncharts.charts';
import * as FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import { ChartViewConfirmedComponent } from '../chart-view-confirmed/chart-view-confirmed.component';
import { ChartViewDeadComponent } from '../chart-view-dead/chart-view-dead.component';
import { NONE_TYPE } from '@angular/compiler/src/output/output_ast';
@Component({
  selector: 'app-viewdata',
  templateUrl: './viewdata.component.html',
  styleUrls: ['./viewdata.component.css']  
})
export class ViewdataComponent implements OnInit {
  Datas: any;
  Result: any;
  DeathDatas: any;
  flag= true;deadflag= true;
  faFileMedical=faFileMedical;
  faCheckCircle=faCheckCircle;
  faUserSlash=faUserSlash;
  faChartLine=faChartLine;
  componentRef: any;
  @ViewChild('chartcontainer', { read: ViewContainerRef, static:true}) confirmedEntry: ViewContainerRef;
  @ViewChild('deadchartcontainer', { read: ViewContainerRef, static:true}) deadEntry: ViewContainerRef;
  constructor(private service:CoronaDataService, private resolver: ComponentFactoryResolver) { }
  
  ngOnInit() {
    let resp=this.service.getData();
    resp.subscribe(data=>{
      this.Datas = data;
    });
    let deathD=this.service.getDeathData();
    deathD.subscribe(data=>{
      this.DeathDatas = data;
    });
    let result=this.service.getResult();
    result.subscribe(data=>{
      this.Result=data;
    }); 
   }
  loadChart() {   
    console.log("clicked"); 
    if(!this.flag){     
      this.confirmedEntry.clear();
      this.flag =true;
    }
    else{
      this.confirmedEntry.clear();
      const factory = this.resolver.resolveComponentFactory(ChartViewConfirmedComponent);
      this.componentRef = this.confirmedEntry.createComponent(factory);
      if(this.flag==true) this.flag =false;
    }    
  }
  loadDeadChart() {   
    console.log("clicked"); 
    if(!this.deadflag){     
      this.deadEntry.clear();
      this.deadflag =true;
    }
    else{
      this.deadEntry.clear();
      const factory = this.resolver.resolveComponentFactory(ChartViewDeadComponent);
      this.componentRef = this.deadEntry.createComponent(factory);
      if(this.deadflag==true) this.deadflag =false;
    }    
  }
  destroyComponent() {
    this.componentRef.destroy();
  }
  
}

