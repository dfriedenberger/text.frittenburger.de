package de.frittenburger.text.impl;

import java.util.List;

import de.frittenburger.text.interfaces.Mapper;
import de.frittenburger.text.model.Token;

public class TokenToMaxLevelMapper implements Mapper<List<Token>, Integer> {

	@Override
	public Integer map(List<Token> tokens) {
		int max = -1;
		for(Token token : tokens)
			max = Math.max(max, token.getLevel()); 
		return max;
	}

}
