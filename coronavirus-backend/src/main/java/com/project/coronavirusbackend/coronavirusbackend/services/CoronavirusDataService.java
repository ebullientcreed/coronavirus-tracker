package com.project.coronavirusbackend.coronavirusbackend.services;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//making this as a spring service

import com.project.coronavirusbackend.coronavirusbackend.models.CountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.LocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.deathCountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.deathLocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.resultStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.timeSeriesData;


@Service
public class CoronavirusDataService {
	public String getRawDataUrl() {
		return rawDataUrl;
	}
	public List<LocationStatistics> getAllStatList() {
		return allStatList;
	}
	public List<CountryStatistics> getAllCountryStat() {
		return allCountryStat;
	}
	private String rawDataUrl="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private String rawDataDeathUrl= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
	private  List<LocationStatistics> allStatList=new ArrayList<>();
	private  List<CountryStatistics> allCountryStat=new ArrayList<>();
	private  List<deathLocationStatistics> allDeathStatList=new ArrayList<>();
	private  List<deathCountryStatistics> allDeathCountryStat=new ArrayList<>();
	private  List<resultStatistics> resultCountry=new ArrayList<>();
	public List<resultStatistics> getresultCountry() {		
		return resultCountry;
	}
	public List<deathLocationStatistics> getAllDeathStatList() {
		return allDeathStatList;
	}
	public List<deathCountryStatistics> getAllDeathCountryStat() {
		return allDeathCountryStat;
	}
	public List<timeSeriesData> getTimeData() {
		return timeData;
	}
	public HashMap<String, Integer> getConfirmedTimeData() {
		return confirmedTimeData;
	}
	public HashMap<String, Integer> getDeadTimeData() {
		return deadTimeData;
	}
	private List<timeSeriesData> timeData=new ArrayList<>();
	private HashMap<String,Integer> confirmedTimeData=new LinkedHashMap<>();
	private HashMap<String,Integer> deadTimeData=new LinkedHashMap<>();
	
	
	//Execute after the creation of service, after instance of class run this method
	@PostConstruct
	//Run of method on regular basis, it runs on every second by setting cron variable as below
	//second minute hr . . .
	@Scheduled(cron="* * 1 * * *")
	public void fetchCoronaData() throws NumberFormatException,IOException, InterruptedException {
		//create http client
		//and use http request to build builderpattern providing location
		List<LocationStatistics> newStatList=new ArrayList<>();
		TreeMap<String,Integer> newCountryStatMap=new TreeMap<>();
		HashMap<String,Integer> newTimeData=new LinkedHashMap<>();		
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				.uri(URI.create(rawDataUrl))
				.build();
		//send response ,returning htpp response as string
		HttpResponse<String> httpresponse=client.send(request, HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpresponse.body());
		//using a csv library from apache commons to read csv body
		StringReader csvBReader=new StringReader(httpresponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBReader);
		Set<String> headers = records.iterator().next().toMap().keySet();
		String heads[]=new String[headers.size()];
		int i=0;
		for(String head:headers) {
			heads[i]=head;
			i++;
		}
		for(int j=(headers.size()-31);j<headers.size();j++) {
			newTimeData.put(heads[j],0);
		}		
		for (CSVRecord record : records) {
			for(int k=(record.size()-31);k<record.size();k++) {
				newTimeData.put(heads[k],newTimeData.getOrDefault(heads[k],0)+Integer.parseInt(record.get(k)));
			}
			LocationStatistics locStat=new LocationStatistics();			
		    locStat.setState(record.get("Province/State"));
		    locStat.setCountry(record.get("Country/Region"));		    
		    int latestCases=Integer.parseInt(record.get(record.size()-1));
		    int prevDayCases=Integer.parseInt(record.get(record.size()-2));
		    locStat.setLatestTotalCases(latestCases);
		    locStat.setChangeFromPrevDay(latestCases-prevDayCases);
		    newStatList.add(locStat);
		    newCountryStatMap.put(record.get("Country/Region"),newCountryStatMap.getOrDefault(record.get("Country/Region"), 0)+latestCases);		    
		}
		this.allStatList=newStatList;
		this.confirmedTimeData=newTimeData;
		List<CountryStatistics> newCountryStat=new ArrayList<>();
		for(String country:newCountryStatMap.keySet()) {
			CountryStatistics countStat=new CountryStatistics();
			countStat.setCountry(country);
			countStat.setLatestTotalCases(newCountryStatMap.get(country));
			newCountryStat.add(countStat);
		}
		this.allCountryStat=newCountryStat;
		fetchCoronaDeathData();
		fetchResult();
	}
//	//Execute after the creation of service, after instance of class run this method
//	@PostConstruct
//	//Run of method on regular basis, it runs on every second by setting cron variable as below
//	//second minute hr . . .
//	@Scheduled(cron="* * 1 * * *")
	public void fetchCoronaDeathData() throws NumberFormatException,IOException, InterruptedException {
		//create http client
		//and use http request to build builderpattern providing location
		List<deathLocationStatistics> newStatList=new ArrayList<>();
		TreeMap<String,Integer> newCountryStatMap=new TreeMap<>();
		HashMap<String,Integer> newdeadTimeData=new LinkedHashMap<>();
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				.uri(URI.create(rawDataDeathUrl))
				.build();
		//send response ,returning htpp response as string
		HttpResponse<String> httpresponse=client.send(request, HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpresponse.body());
		//using a csv library from apache commons to read csv body
		StringReader csvBReader=new StringReader(httpresponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBReader);
		Set<String> headers = records.iterator().next().toMap().keySet();
		String heads[]=new String[headers.size()];
		int i=0;
		for(String head:headers) {
			heads[i]=head;
			i++;
		}
		for(int j=(headers.size()-31);j<headers.size();j++) {
			newdeadTimeData.put(heads[j],0);
		}	
		for (CSVRecord record : records) {
			for(int k=(record.size()-31);k<record.size();k++) {
				newdeadTimeData.put(heads[k],newdeadTimeData.getOrDefault(heads[k],0)+Integer.parseInt(record.get(k)));
			}
			deathLocationStatistics locStat=new deathLocationStatistics();
		    locStat.setState(record.get("Province/State"));
		    locStat.setCountry(record.get("Country/Region"));		    
		    int latestCases=Integer.parseInt(record.get(record.size()-1));
		    int prevDayCases=Integer.parseInt(record.get(record.size()-2));
		    locStat.setLatestTotalCases(latestCases);
		    locStat.setChangeFromPrevDay(latestCases-prevDayCases);
		    newStatList.add(locStat);
		    newCountryStatMap.put(record.get("Country/Region"),newCountryStatMap.getOrDefault(record.get("Country/Region"), 0)+latestCases);		    
		}
		this.allDeathStatList=newStatList;
		this.deadTimeData=newdeadTimeData;
		List<deathCountryStatistics> newCountryStat=new ArrayList<>();
		for(String country:newCountryStatMap.keySet()) {
			deathCountryStatistics countStat=new deathCountryStatistics();
			countStat.setCountry(country);
			countStat.setLatestTotalDeathCases(newCountryStatMap.get(country));
			newCountryStat.add(countStat);
		}
		this.allDeathCountryStat=newCountryStat;
	}
//	//Execute after the creation of service, after instance of class run this method
//	@PostConstruct
//	//Run of method on regular basis, it runs on every second by setting cron variable as below
//	//second minute hr . . .
//	@Scheduled(cron="* * 1 * * *")
	public void fetchResult() throws IOException, InterruptedException {
		List<CountryStatistics> confirmedList=getAllCountryStat();
		List<deathCountryStatistics> deathList=getAllDeathCountryStat();
		List<resultStatistics> resultList=new ArrayList<>();
		for(int i=0;i<confirmedList.size();i++) {
			if(confirmedList.get(i).getCountry().equals(deathList.get(i).getCountry())) {
				resultStatistics rs=new resultStatistics();				
				rs.setNumber(i+1);
				rs.setCountry(confirmedList.get(i).getCountry());
				rs.setLatestTotalCases(confirmedList.get(i).getLatestTotalCases());
				rs.setLatestTotalDeathCases(deathList.get(i).getLatestTotalDeathCases());
				resultList.add(rs);
			}
		}
		this.resultCountry=resultList;
	}
}
