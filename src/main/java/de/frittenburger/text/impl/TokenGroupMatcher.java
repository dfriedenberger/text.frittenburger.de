package de.frittenburger.text.impl;


import de.frittenburger.text.model.Token;
import de.frittenburger.text.model.TokenGroup;

public class TokenGroupMatcher {

	public boolean match(TokenGroup expectedGroup, TokenGroup currentGroup) {

		
		if(expectedGroup.getTokens().size() != currentGroup.getTokens().size())
			return false;
		
		for(int i = 0;i < expectedGroup.getTokens().size();i++)
		{
			Token tExpected = expectedGroup.getTokens().get(i);
			Token current = currentGroup.getTokens().get(i);
			
			if(!match(tExpected,current)) 
				return false;
			
		}
		
		return true;
	}

	private boolean match(Token expected, Token current) {

		
		if(!current.getNamedEntity().equals(expected.getNamedEntity()))
			return false;
		if(!current.getPartOfSpeech().equals(expected.getPartOfSpeech()))
			return false;
		
		//WildCard is posible
		if(expected.getText().equals("*")) 
			return true;
		if(!current.getText().equals(expected.getText()))
			return false;
		
		return true;
	}

}
