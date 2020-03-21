package com.project.coronavirusbackend.coronavirusbackend.models;

public class CountryStatistics{	
	private String country;
	private int latestTotalCases;
	private int changeFromPrevDay;
	
	
	public int getChangeFromPrevDay() {
		return changeFromPrevDay;
	}
	public void setChangeFromPrevDay(int changeFromPrevDay) {
		this.changeFromPrevDay = changeFromPrevDay;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
}
