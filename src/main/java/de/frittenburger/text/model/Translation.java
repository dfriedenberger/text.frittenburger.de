package de.frittenburger.text.model;

import java.util.List;

public class Translation {
	
	private String origin;
	private List<String> candidates;
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public List<String> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<String> candidates) {
		this.candidates = candidates;
	}

	@Override
	public String toString() {
		return "Translation [origin=" + origin 
				+ ", candidates=" + candidates + "]";
	}

	
	
}
