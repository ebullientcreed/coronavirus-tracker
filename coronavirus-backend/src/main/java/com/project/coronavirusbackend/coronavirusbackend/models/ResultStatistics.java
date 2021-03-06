package com.project.coronavirusbackend.coronavirusbackend.models;

public class ResultStatistics {
	private int number;	
	private String country;
	private int latestTotalCases;
	private int latestTotalDeathCases;
	private int latestRecoveredCases;
	public int getLatestRecoveredCases() {
		return latestRecoveredCases;
	}
	public void setLatestRecoveredCases(int latestRecoveredCases) {
		this.latestRecoveredCases = latestRecoveredCases;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
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
	public int getLatestTotalDeathCases() {
		return latestTotalDeathCases;
	}
	public void setLatestTotalDeathCases(int latestTotalDeathCases) {
		this.latestTotalDeathCases = latestTotalDeathCases;
	}
}
