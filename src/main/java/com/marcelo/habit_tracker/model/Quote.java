package com.marcelo.habit_tracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String q;
    private String a;


    // Getters and setters
    public void setQ(String q) {
		this.q = q;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getQ() {
        return q;
    }

    public String getA() {
        return a;
    }
}