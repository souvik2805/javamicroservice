package com.asl.movieinfoservice.models;

public class MovieSummary {

	private String title;
	private String overview;

	public MovieSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieSummary(String title, String overview) {
		super();
		this.title = title;
		this.overview = overview;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

}
