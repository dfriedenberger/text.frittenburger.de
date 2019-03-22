package de.frittenburger.text.impl;

import java.util.List;

import de.frittenburger.text.interfaces.Mapper;
import de.frittenburger.text.model.Token;

public class TokenToTextMapper implements Mapper<List<Token>, String> {

	@Override
	public String map(List<Token> tokens) {

		String text = null;
		for(Token token : tokens)
		{
			if(text == null)
				text = token.getText();
			else
				text += " " + token.getText();
		}
		return text;
	}

}
