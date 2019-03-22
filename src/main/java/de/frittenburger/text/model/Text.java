package de.frittenburger.text.model;

import java.util.ArrayList;
import java.util.List;

public class Text {

	
	private List<Sentence> sentences;
	
	
	

	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(List<Sentence> sentences) {
		this.sentences = sentences;
	}
	
	public void addSentencesItem(Sentence sentence) {

		if(sentences == null)
			sentences = new ArrayList<Sentence>();
		sentences.add(sentence);
	}
}
