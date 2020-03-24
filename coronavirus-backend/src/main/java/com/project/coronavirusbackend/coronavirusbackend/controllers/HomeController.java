package com.project.coronavirusbackend.coronavirusbackend.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.coronavirusbackend.coronavirusbackend.models.ChartData;
import com.project.coronavirusbackend.coronavirusbackend.models.CountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.LocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.RecoveredCountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.RecoveredLocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.DeathCountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.DeathLocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.ResultStatistics;
import com.project.coronavirusbackend.coronavirusbackend.services.CoronavirusDataService;

@RestController
public class HomeController {
	@Autowired
	CoronavirusDataService coronaDataService;
	
	@GetMapping("/getData")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Object> getData(){
		List<Object> resultList=new ArrayList<Object>();
		List<LocationStatistics> allTotal=coronaDataService.getAllStatList();
		int totalCases=allTotal.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
		int totalPrevCases=allTotal.stream().mapToInt(stat->stat.getChangeFromPrevDay()).sum();
		//resultList.add(coronaDataService.getAllStatList());
		resultList.add(totalCases);
		resultList.add(totalPrevCases);
		resultList.add(coronaDataService.getAllCountryStat());
		return resultList;
	}
	@GetMapping("/getCountryData")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<CountryStatistics> countryData(Model model){
		List<CountryStatistics> resultList=new ArrayList<CountryStatistics>();		
		resultList.addAll(coronaDataService.getAllCountryStat());
		return resultList;
	}
	@GetMapping("/getDeathData")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Object> getDeathData(){
		List<Object> resultList=new ArrayList<Object>();
		List<DeathLocationStatistics> allTotal=coronaDataService.getAllDeathStatList();
		int totalCases=allTotal.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
		int totalPrevCases=allTotal.stream().mapToInt(stat->stat.getChangeFromPrevDay()).sum();
		//resultList.add(coronaDataService.getAllStatList());
		resultList.add(totalCases);
		resultList.add(totalPrevCases);
		resultList.add(coronaDataService.getAllDeathCountryStat());
		return resultList;
	}
	@GetMapping("/getCountryDeathData")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<DeathCountryStatistics> countryDeathData(Model model){
		List<DeathCountryStatistics> resultList=new ArrayList<DeathCountryStatistics>();		
		resultList.addAll(coronaDataService.getAllDeathCountryStat());
		return resultList;
	}
	@GetMapping("/getRecoveryData")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<Object> recoverdData(){
		List<Object> resultList=new ArrayList<Object>();
		List<RecoveredLocationStatistics> allTotal=coronaDataService.getAllRecoveredStatList();
		int totalCases=allTotal.stream().mapToInt(stat->stat.getLatestTotalCases()).sum();
		int totalPrevCases=allTotal.stream().mapToInt(stat->stat.getChangeFromPrevDay()).sum();
		//resultList.add(coronaDataService.getAllStatList());
		resultList.add(totalCases);
		resultList.add(totalPrevCases);
		resultList.add(coronaDataService.getAllRecoveredCountryStat());
		return resultList;
	}
	@GetMapping("/getCountryRecoveredData")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<RecoveredCountryStatistics> countryRecoverdData(){
		List<RecoveredCountryStatistics> resultList=new ArrayList<RecoveredCountryStatistics>();		
		resultList.addAll(coronaDataService.getAllRecoveredCountryStat());
		return resultList;
	}
	@GetMapping("/getResult")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<ResultStatistics> resultData(){
		List<ResultStatistics> resultList=new ArrayList<ResultStatistics>();		
		resultList.addAll(coronaDataService.getresultCountry());
		return resultList;
	}
	@GetMapping("/getTimeConfirmedCases")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<ChartData> confirmedTimeData(){
		HashMap<String, Integer> map=coronaDataService.getConfirmedTimeData();
		List<ChartData> res=new ArrayList<>();
		for(String date:map.keySet()) {
			ChartData cd=new ChartData();
			cd.setLabel(date);
			cd.setValue(String.valueOf(map.get(date)));
			res.add(cd);
		}
		return res;
	}
	@GetMapping("/getTimeDeathCases")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<ChartData> deathTimeData(){
		HashMap<String, Integer> map=coronaDataService.getDeadTimeData();
		List<ChartData> res=new ArrayList<>();
		for(String date:map.keySet()) {
			ChartData cd=new ChartData();
			cd.setLabel(date);
			cd.setValue(String.valueOf(map.get(date)));
			res.add(cd);
		}
		return res;
	}
	@GetMapping("/getTimeRecoveredCases")
	@CrossOrigin(origins = "http://localhost:4200")
	public List<ChartData> recoveredTimeData(){
		HashMap<String, Integer> map=coronaDataService.getRecoveredTimeData();
		List<ChartData> res=new ArrayList<>();
		for(String date:map.keySet()) {
			ChartData cd=new ChartData();
			cd.setLabel(date);
			cd.setValue(String.valueOf(map.get(date)));
			res.add(cd);
		}
		return res;
	}
} 
