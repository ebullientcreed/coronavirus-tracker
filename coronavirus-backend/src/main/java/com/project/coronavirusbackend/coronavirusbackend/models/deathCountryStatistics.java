package com.project.coronavirusbackend.coronavirusbackend.models;

public class deathCountryStatistics {
	private String deathcountry;
	private int latestTotalDeathCases;
	private int  deathChangeFromPrevDay;
	
	public int getLatestTotalDeathCases() {
		return latestTotalDeathCases;
	}
	public void setLatestTotalDeathCases(int latestTotalDeathCases) {
		this.latestTotalDeathCases = latestTotalDeathCases;
	}
	public int getDeathChangeFromPrevDay() {
		return deathChangeFromPrevDay;
	}
	public void setDeathChangeFromPrevDay(int deathChangeFromPrevDay) {
		this.deathChangeFromPrevDay = deathChangeFromPrevDay;
	}
	public String getCountry() {
		return deathcountry;
	}
	public void setCountry(String country) {
		this.deathcountry = country;
	}
}
