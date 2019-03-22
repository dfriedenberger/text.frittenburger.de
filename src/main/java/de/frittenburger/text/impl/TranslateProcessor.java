package de.frittenburger.text.impl;

import java.util.List;
import java.util.function.Function;

import de.frittenburger.text.interfaces.Mapper;
import de.frittenburger.text.interfaces.TranslateService;
import de.frittenburger.text.model.Token;
import de.frittenburger.text.model.TokenGroup;
import de.frittenburger.text.model.Translation;


public class TranslateProcessor implements Function<TokenGroup,Translation> {

	private final TranslateProcessor nextProcessor;
	private final TranslateService translateService;
	private final Mapper<List<Token>,String> toTextMapper = new TokenToTextMapper();
	private final Mapper<List<Token>,Integer> toMaxLevelMapper = new TokenToMaxLevelMapper();
	
	public TranslateProcessor(TranslateService translateService,TranslateProcessor nextProcessor) {
		this.translateService = translateService;
		this.nextProcessor = nextProcessor;
	}

	public TranslateProcessor(TranslateService translateService) {
		this.translateService = translateService;
		this.nextProcessor = null;
	}
	
	@Override
	public Translation apply(TokenGroup tokenGroup) {

		if(!tokenGroup.getTranslate()) 
			return null;
		
		int level = toMaxLevelMapper.map(tokenGroup.getTokens());
		if(level < 9) return null;
		
		String text = toTextMapper.map(tokenGroup.getTokens());
		
		Translation translation = translateService.translate(text); 
		if(translation != null)
			return translation;
		
		if(nextProcessor != null)
			return nextProcessor.apply(tokenGroup);
		
		return null;
	}
	

}
