package com.project.coronavirusbackend.coronavirusbackend.models;

public class timeSeriesData {
	private String onDate;
	private int totalConfirmed;
	public String getOnDate() {
		return onDate;
	}
	public void setOnDate(String onDate) {
		this.onDate = onDate;
	}
	public int getTotalConfirmed() {
		return totalConfirmed;
	}
	public void setTotalConfirmed(int totalConfirmed) {
		this.totalConfirmed = totalConfirmed;
	}
	public int getTotalDeath() {
		return totalDeath;
	}
	public void setTotalDeath(int totalDeath) {
		this.totalDeath = totalDeath;
	}
	private int totalDeath;
}
