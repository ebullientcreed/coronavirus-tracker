package com.project.coronavirusbackend.coronavirusbackend.models;

public class RecoveredCountryStatistics {
	private String country;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalRecoveredCases() {
		return latestTotalRecoveredCases;
	}
	public void setLatestTotalRecoveredCases(int latestTotalRecoveredCases) {
		this.latestTotalRecoveredCases = latestTotalRecoveredCases;
	}
	public int getRecoveryChangeFromPrevDay() {
		return recoveryChangeFromPrevDay;
	}
	public void setRecoveryChangeFromPrevDay(int recoveryChangeFromPrevDay) {
		this.recoveryChangeFromPrevDay = recoveryChangeFromPrevDay;
	}
	private int latestTotalRecoveredCases;
	private int  recoveryChangeFromPrevDay;
}
