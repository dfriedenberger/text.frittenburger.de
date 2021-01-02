package de.frittenburger.text.model;

import java.util.ArrayList;
import java.util.List;

public class Sentence {

	private String text;

	private List<Token> tokens;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
	}

	public void addTokensItem(Token token) {
		if(tokens == null)	
			tokens = new ArrayList<Token>();
		tokens.add(token);
	}

	
}
