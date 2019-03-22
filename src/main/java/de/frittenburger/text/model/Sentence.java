package de.frittenburger.text.model;

import java.util.ArrayList;
import java.util.List;

public class Sentence {

	private List<Token> tokens;

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
