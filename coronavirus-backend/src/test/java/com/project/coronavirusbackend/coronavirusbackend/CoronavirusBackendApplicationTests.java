package com.project.coronavirusbackend.coronavirusbackend;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions.*;

import com.project.coronavirusbackend.coronavirusbackend.models.CountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.LocationStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.deathCountryStatistics;
import com.project.coronavirusbackend.coronavirusbackend.models.resultStatistics;
import com.project.coronavirusbackend.coronavirusbackend.services.CoronavirusDataService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CoronavirusBackendApplicationTests {

	@Autowired
	CoronavirusDataService coronavirusdataservice;
	@Test
	void getAllStatListTest() {
		LocationStatistics ls=new LocationStatistics();
		ls.setState("");
		ls.setCountry("USA");
		ls.setLatestTotalCases(1000);
		ls.setChangeFromPrevDay(100);
		List<LocationStatistics> list=new ArrayList<LocationStatistics>();
		list.add(ls);
		coronavirusdataservice.setAllStatList(list);
	    Assertions.assertEquals(1, coronavirusdataservice.getAllStatList().size());
	}
	
	@Test
	void getRawDataUrlTest() throws IOException, InterruptedException {
		String rawData=coronavirusdataservice.getRawDataUrl();		
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				.uri(URI.create(rawData))
				.build();
		//send response ,returning http response as string
		HttpResponse<String> httpresponse=client.send(request, HttpResponse.BodyHandlers.ofString());
		Assertions.assertNotEquals(0, httpresponse.toString().length());
	}
	@Test
	void getRawDeathDataUrlTest() throws IOException, InterruptedException {
		String rawData=coronavirusdataservice.getRawDataDeathUrl();		
		HttpClient client=HttpClient.newHttpClient();
		HttpRequest request=HttpRequest.newBuilder()
				.uri(URI.create(rawData))
				.build();
		//send response ,returning http response as string
		HttpResponse<String> httpresponse=client.send(request, HttpResponse.BodyHandlers.ofString());
		Assertions.assertNotEquals(0, httpresponse.toString().length());
	}
//	@Test
//	void fetchResultTest() throws IOException, InterruptedException {
//		List<CountryStatistics> confirmedList=new ArrayList<>();
//		List<deathCountryStatistics> deathList=new ArrayList<>();
//		CountryStatistics cs=new CountryStatistics();
//		cs.setCountry("USA");
//		cs.setLatestTotalCases(1000);
//		cs.setChangeFromPrevDay(100);
//		deathCountryStatistics dcs=new deathCountryStatistics();
//		dcs.setCountry("USA");
//		dcs.setLatestTotalDeathCases(120);
//		dcs.setDeathChangeFromPrevDay(10);
//		confirmedList.add(cs);
//		deathList.add(dcs);
//		resultStatistics rs=new resultStatistics();
//		rs.setNumber(1);
//		rs.setCountry("USA");
//		rs.setLatestTotalCases(1000);
//		rs.setLatestTotalDeathCases(120);
//		List<resultStatistics> resultList=new ArrayList<>();
//		resultList.add(rs);
//		coronavirusdataservice.fetchResult(confirmedList, deathList);
//		Assertions.assertEquals(resultList,coronavirusdataservice.getresultCountry());
//	}	

}
