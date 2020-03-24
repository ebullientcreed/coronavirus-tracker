import { Component, OnInit,ViewChild,
  ViewContainerRef,ComponentFactoryResolver,ComponentRef,
  ComponentFactory} from '@angular/core';
import { CoronaDataService } from '../corona-data.service';
import { faFileMedical,faUserPlus,faUserSlash,faChartLine, faUserCheck } from '@fortawesome/free-solid-svg-icons';
//import { FusionCharts } from "angular-fusioncharts";
import * as FusionCharts from 'fusioncharts';
import * as charts from 'fusioncharts/fusioncharts.charts';
import * as FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import { ChartViewConfirmedComponent } from '../chart-view-confirmed/chart-view-confirmed.component';
import { ChartViewDeadComponent } from '../chart-view-dead/chart-view-dead.component';
import { ChartViewRecoveredComponent } from '../chart-view-recovered/chart-view-recovered.component';
import { ChartViewAllComponent } from '../chart-view-all/chart-view-all.component';
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
  RecoveredDatas: any;
  allFlag=true;flag= true;deadflag= true;recoveredFlag=true;
  faFileMedical=faFileMedical;faUserPlus=faUserPlus;
  faUserSlash=faUserSlash;faChartLine=faChartLine;faUserCheck=faUserCheck;
  componentRef: any;
  @ViewChild('chartcontainer', { read: ViewContainerRef, static:true}) confirmedEntry: ViewContainerRef;
  @ViewChild('deadchartcontainer', { read: ViewContainerRef, static:true}) deadEntry: ViewContainerRef;
  @ViewChild('recoveredchartcontainer', { read: ViewContainerRef, static:true}) recoveredEntry: ViewContainerRef;
  @ViewChild('allchartcontainer', { read: ViewContainerRef, static:true}) allEntry: ViewContainerRef;
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
    let recoveredData=this.service.getRecoveredData();
    recoveredData.subscribe(data=>{
      this.RecoveredDatas = data;
    });
    let result=this.service.getResult();
    result.subscribe(data=>{
      this.Result=data;
    }); 
    
   }
   loadAllChart(){
    if(!this.allFlag){     
      this.allEntry.clear();
      this.allFlag =true;
    }
    else{
      this.confirmedEntry.clear();
      const factory = this.resolver.resolveComponentFactory(ChartViewAllComponent);
      this.componentRef = this.allEntry.createComponent(factory);
      if(this.allFlag==true) this.allFlag =false;
    } 
   }
  loadChart() {   
   // console.log("clicked"); 
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
    //console.log("clicked"); 
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
  loadRecoveredChart(){
    //console.log("clicked"); 
    if(!this.recoveredFlag){     
      this.recoveredEntry.clear();
      this.recoveredFlag =true;
    }
    else{
      this.recoveredEntry.clear();
      const factory = this.resolver.resolveComponentFactory(ChartViewRecoveredComponent);
      this.componentRef = this.recoveredEntry.createComponent(factory);
      if(this.recoveredFlag==true) this.recoveredFlag =false;
    }   
  }
  destroyComponent() {
    this.componentRef.destroy();
  }
  
}

