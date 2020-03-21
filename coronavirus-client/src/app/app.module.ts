import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ViewdataComponent } from './viewdata/viewdata.component';
import { CoronaDataService } from './corona-data.service';
import { HttpClientModule } from '@angular/common/http';
import { TablePaginationComponent } from './table-pagination/table-pagination.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatTableModule } from '@angular/material'  
import { MatPaginatorModule } from '@angular/material';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { MatSortModule,MatFormFieldModule, MatInputModule } from '@angular/material';
// Import FusionCharts library and chart modules
import { FusionChartsModule } from "angular-fusioncharts";
import * as FusionCharts from "fusioncharts";
import * as charts from "fusioncharts/fusioncharts.charts";
import * as FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import { ChartViewConfirmedComponent } from './chart-view-confirmed/chart-view-confirmed.component';
import { ChartViewDeadComponent } from './chart-view-dead/chart-view-dead.component';
import { CoronaFooterComponent } from './corona-footer/corona-footer.component';
FusionChartsModule.fcRoot(FusionCharts, charts, FusionTheme);
@NgModule({
  declarations: [
    AppComponent,
    ViewdataComponent,
    TablePaginationComponent,
    ChartViewConfirmedComponent,
    ChartViewDeadComponent,
    CoronaFooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatPaginatorModule,
    FontAwesomeModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
    FusionChartsModule
  ],
  providers: [CoronaDataService],
  bootstrap: [AppComponent],
  entryComponents: [ChartViewConfirmedComponent,ChartViewDeadComponent]
})
export class AppModule { }
