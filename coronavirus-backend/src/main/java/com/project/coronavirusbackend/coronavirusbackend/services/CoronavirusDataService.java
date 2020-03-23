package com.project.coronavirusbackend.coronavirusbackend.services;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//making this as a spring service

import com.project.coronavirusbackend.coronavirusbackend.models.CountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.LocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.RecoveredCountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.RecoveredLocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.DeathCountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.DeathLocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.ResultStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.TimeSeriesData;


@Service
public class CoronavirusDataService {	
	private String rawDataUrl="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	private String rawDataDeathUrl= "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Deaths.csv";
	private String rawDataRecoverdUrl="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";
	private  List<LocationStatistics> allStatList=new ArrayList<>();
	private  List<CountryStatistics> allCountryStat=new ArrayList<>();
	private  List<DeathLocationStatistics> allDeathStatList=new ArrayList<>();
	private  List<DeathCountryStatistics> allDeathCountryStat=new ArrayList<>();
	private  List<ResultStatistics> resultCountry=new ArrayList<>();
	private List<TimeSeriesData> timeData=new ArrayList<>();
	private HashMap<String,Integer> confirmedTimeData=new LinkedHashMap<>();
	private HashMap<String,Integer> deadTimeData=new LinkedHashMap<>();
	private List<RecoveredLocationStatistics> allRecoveredStatList;	
	private  List<RecoveredCountryStatistics> allRecoveredCountryStat=new ArrayList<>();
	private HashMap<String, Integer> recoveredTimeData;
	public List<RecoveredLocationStatistics> getAllRecoveredStatList() {
		return allRecoveredStatList;
	}
	public HashMap<String, Integer> getRecoveredTimeData() {
		return recoveredTimeData;
	}
	public List<RecoveredCountryStatistics> getAllRecoveredCountryStat() {
		return allRecoveredCountryStat;
	}
	public void setAllRecoveredCountryStat(List<RecoveredCountryStatistics> allRecoveredCountryStat) {
		this.allRecoveredCountryStat = allRecoveredCountryStat;
	}
	public String getRawDataRecoverdUrl() {
		return rawDataRecoverdUrl;
	}
	public void setRawDataRecoverdUrl(String rawDataRecoverdUrl) {
		this.rawDataRecoverdUrl = rawDataRecoverdUrl;
	}
	public String getRawDataUrl() {
		return rawDataUrl;
	}
	public String getRawDataDeathUrl() {
		return rawDataDeathUrl;
	}
	
	public List<LocationStatistics> getAllStatList() {
		return allStatList;
	}
	public void setAllStatList(List<LocationStatistics> allStatList) {
		this.allStatList = allStatList;
	}
	public List<CountryStatistics> getAllCountryStat() {
		return allCountryStat;
	}
	public List<ResultStatistics> getresultCountry() {		
		return resultCountry;
	}
	public List<DeathLocationStatistics> getAllDeathStatList() {
		return allDeathStatList;
	}
	public List<DeathCountryStatistics> getAllDeathCountryStat() {
		return allDeathCountryStat;
	}
	public List<TimeSeriesData> getTimeData() {
		return timeData;
	}
	public HashMap<String, Integer> getConfirmedTimeData() {
		return confirmedTimeData;
	}
	public HashMap<String, Integer> getDeadTimeData() {
		return deadTimeData;
	}	

	private static final Logger log = LoggerFactory.getLogger(CoronavirusDataService.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	//Execute after the creation of service, after instance of class run this method
	@PostConstruct
	//Runs on 1st hour of every day
	//Run of method on regular basis, it runs on every second by setting cron variable as below
	//second minute hr . . .
	@Scheduled(cron="* * 1 * * *")
	public void fetchCoronaData() throws NumberFormatException,IOException, InterruptedException {
		//logging the time for each schedule
		log.info("The time is now {}", dateFormat.format(new Date()));
		List<LocationStatistics> newStatList=new ArrayList<>();
		TreeMap<String,Integer> newCountryStatMap=new TreeMap<>();
		HashMap<String,Integer> newTimeData=new LinkedHashMap<>();		
		//create http client
		//and use http request to build builderpattern providing location
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				.uri(URI.create(rawDataUrl))
				.build();
		//send response ,returning http response as string
		HttpResponse<String> httpresponse=client.send(request, HttpResponse.BodyHandlers.ofString());
		//System.out.println(httpresponse.body());
		//using a csv library from apache commons to read csv body
		StringReader csvBReader=new StringReader(httpresponse.body());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBReader);
		//extracting the headers from the records
		Set<String> headers = records.iterator().next().toMap().keySet();
		String heads[]=new String[headers.size()];
		int i=0;
		for(String head:headers) {
			heads[i]=head;
			i++;
		}
		// updating the time data map with the dates(last 31 days) from the headers  and assigning 0 for each date
		for(int j=(headers.size()-31);j<headers.size();j++) {
			newTimeData.put(heads[j],0);
		}		
		for (CSVRecord record : records) {
			//for each record updating the value for each dates
			for(int k=(record.size()-31);k<record.size();k++) {
				newTimeData.put(heads[k],newTimeData.getOrDefault(heads[k],0)+Integer.parseInt(record.get(k)));
			}
			//Creating an object of locationstatistics class
			LocationStatistics locStat=new LocationStatistics();
			//setting the values from record datas
		    locStat.setState(record.get("Province/State"));
		    locStat.setCountry(record.get("Country/Region"));		    
		    int latestCases=Integer.parseInt(record.get(record.size()-1));
		    int prevDayCases=Integer.parseInt(record.get(record.size()-2));
		    locStat.setLatestTotalCases(latestCases);
		    locStat.setChangeFromPrevDay(latestCases-prevDayCases);
		    newStatList.add(locStat);
		    //accumulating the data for each country and updating each country data
		    newCountryStatMap.put(record.get("Country/Region"),newCountryStatMap.getOrDefault(record.get("Country/Region"), 0)+latestCases);		    
		}
		this.allStatList=newStatList;
		this.confirmedTimeData=newTimeData;
		List<CountryStatistics> newCountryStat=new ArrayList<>();
		//for each country data, create countrystatics object and make a list for each country 
		for(String country:newCountryStatMap.keySet()) {
			CountryStatistics countStat=new CountryStatistics();
			countStat.setCountry(country);
			countStat.setLatestTotalCases(newCountryStatMap.get(country));
			newCountryStat.add(countStat);
		}
		this.allCountryStat=newCountryStat;
		//invoke this method to update the death data
		fetchCoronaDeathData();
		fetchCoronaRecoveredData();
		//finally the result method to get the combined list of confirmed and death cases
		fetchResult(getAllCountryStat(),getAllDeathCountryStat(),getAllRecoveredCountryStat());
	}
	public void fetchCoronaDeathData() throws NumberFormatException,IOException, InterruptedException {
		//create http client
		//and use http request to build builderpattern providing location
		List<DeathLocationStatistics> newStatList=new ArrayList<>();
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
		// updating the time data map with the dates(last 31 days) from the headers and assigning 0 for each date
		for(int j=(headers.size()-31);j<headers.size();j++) {
			newdeadTimeData.put(heads[j],0);
		}	
		for (CSVRecord record : records) {
			//for each record updating the value for each dates
			for(int k=(record.size()-31);k<record.size();k++) {
				newdeadTimeData.put(heads[k],newdeadTimeData.getOrDefault(heads[k],0)+Integer.parseInt(record.get(k)));
			}//Creating an object of deathLocationStatistics class
			//and setting the values from record datas
			DeathLocationStatistics locStat=new DeathLocationStatistics();
		    locStat.setState(record.get("Province/State"));
		    locStat.setCountry(record.get("Country/Region"));		    
		    int latestCases=Integer.parseInt(record.get(record.size()-1));
		    int prevDayCases=Integer.parseInt(record.get(record.size()-2));
		    locStat.setLatestTotalCases(latestCases);
		    locStat.setChangeFromPrevDay(latestCases-prevDayCases);
		    newStatList.add(locStat);
		  //accumulating the data for each country and updating each country data
		    newCountryStatMap.put(record.get("Country/Region"),newCountryStatMap.getOrDefault(record.get("Country/Region"), 0)+latestCases);		    
		}
		this.allDeathStatList=newStatList;
		this.deadTimeData=newdeadTimeData;
		List<DeathCountryStatistics> newCountryStat=new ArrayList<>();
		//for each country data, create deathcountrystatics object and make a list for each country 
		for(String country:newCountryStatMap.keySet()) {
			DeathCountryStatistics countStat=new DeathCountryStatistics();
			countStat.setCountry(country);
			countStat.setLatestTotalDeathCases(newCountryStatMap.get(country));
			newCountryStat.add(countStat);
		}
		this.allDeathCountryStat=newCountryStat;
	}
	public void fetchCoronaRecoveredData() throws IOException, InterruptedException {
		//create http client
				//and use http request to build builderpattern providing location
				List<RecoveredLocationStatistics> newStatList=new ArrayList<>();
				TreeMap<String,Integer> newCountryStatMap=new TreeMap<>();
				HashMap<String,Integer> newRecoveredTimeData=new LinkedHashMap<>();
				HttpClient client=HttpClient.newHttpClient();
				HttpRequest request=HttpRequest.newBuilder()
						.uri(URI.create(rawDataRecoverdUrl))
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
				// updating the time data map with the dates(last 31 days) from the headers and assigning 0 for each date
				for(int j=(headers.size()-31);j<headers.size();j++) {
					newRecoveredTimeData.put(heads[j],0);
				}	
				for (CSVRecord record : records) {
					//for each record updating the value for each dates
					for(int k=(record.size()-31);k<record.size();k++) {
						newRecoveredTimeData.put(heads[k],newRecoveredTimeData.getOrDefault(heads[k],0)+Integer.parseInt(record.get(k)));
					}//Creating an object of deathLocationStatistics class
					//and setting the values from record datas
					RecoveredLocationStatistics locStat=new RecoveredLocationStatistics();
				    locStat.setState(record.get("Province/State"));
				    locStat.setCountry(record.get("Country/Region"));		    
				    int latestCases=Integer.parseInt(record.get(record.size()-1));
				    int prevDayCases=Integer.parseInt(record.get(record.size()-2));
				    locStat.setLatestTotalCases(latestCases);
				    locStat.setChangeFromPrevDay(latestCases-prevDayCases);
				    newStatList.add(locStat);
				  //accumulating the data for each country and updating each country data
				    newCountryStatMap.put(record.get("Country/Region"),newCountryStatMap.getOrDefault(record.get("Country/Region"), 0)+latestCases);		    
				}
				this.allRecoveredStatList=newStatList;
				this.recoveredTimeData=newRecoveredTimeData;
				List<RecoveredCountryStatistics> newCountryStat=new ArrayList<>();
				//for each country data, create RecoveredCountryStatistics object and make a list for each country 
				for(String country:newCountryStatMap.keySet()) {
					RecoveredCountryStatistics countStat=new RecoveredCountryStatistics();
					countStat.setCountry(country);
					countStat.setLatestTotalRecoveredCases(newCountryStatMap.get(country));
					newCountryStat.add(countStat);
				}
				this.allRecoveredCountryStat=newCountryStat;
	}
	public void fetchResult(List<CountryStatistics> confirmedList,List<DeathCountryStatistics> deathList, List<RecoveredCountryStatistics> recoveredList) throws IOException, InterruptedException {
		List<ResultStatistics> resultList=new ArrayList<>();
		for(int i=0;i<confirmedList.size();i++) {
			//checks for each list having the same country, resultstatitics is created and total confirmed and dead updated
			if(confirmedList.get(i).getCountry().equals(deathList.get(i).getCountry())) {
				ResultStatistics rs=new ResultStatistics();				
				rs.setNumber(i+1);
				rs.setCountry(confirmedList.get(i).getCountry());
				rs.setLatestTotalCases(confirmedList.get(i).getLatestTotalCases());
				rs.setLatestTotalDeathCases(deathList.get(i).getLatestTotalDeathCases());
				if(confirmedList.get(i).getCountry().equals(recoveredList.get(i).getCountry())){
					rs.setLatestRecoveredCases(recoveredList.get(i).getLatestTotalRecoveredCases());
				}
				resultList.add(rs);
			}
		}
		this.resultCountry=resultList;
	}
}
