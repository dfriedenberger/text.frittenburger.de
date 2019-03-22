package de.frittenburger.text.impl;

import java.util.ArrayList;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.text.interfaces.GroupMatcher;
import de.frittenburger.text.interfaces.VocabularyService;
import de.frittenburger.text.model.Sentence;
import de.frittenburger.text.model.Text;
import de.frittenburger.text.model.Token;
import de.frittenburger.text.model.TokenGroup;

public class VocabularyServiceImpl implements VocabularyService {

	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(VocabularyServiceImpl.class);
	private final GroupMatcher matcher;

	
	public VocabularyServiceImpl(GroupMatcher matcher)
	{
		this.matcher = matcher;
	}

	@Override
	public List<TokenGroup> getVocabulary(Text text) {

		List<TokenGroup> vocabulary = new ArrayList<TokenGroup>();

		
		for(Sentence sentence : text.getSentences())
		{
			 List<Token> tokens = sentence.getTokens();
			 
			 int ix = 0;
			 while(ix < tokens.size())
			 {
				 TokenGroup grp = null;
				 int cnt = 5;
				 while(grp == null && cnt >= 1)
				 {
					 grp = createGroup(tokens,ix,cnt);
				     if(grp != null) break;
				     if(cnt == 1) break;
				     cnt--;
				 }
				 if(grp == null) throw new NullPointerException();
				 
				 //compress
				 if(!vocabulary.isEmpty() && !vocabulary.get(vocabulary.size() - 1).getTranslate() && !grp.getTranslate())
				 {
					 for(Token token : grp.getTokens())
					 {
						 vocabulary.get(vocabulary.size() - 1).addTokensItem(token);
					 }
				 }
				 else
				 {
					 vocabulary.add(grp);
				 }
				 ix += cnt;
			 }
		}
		
		
		return vocabulary;
	}

	private TokenGroup createGroup(List<Token> tokens, int ix, int cnt) {
		
		if(tokens.size() < ix + cnt) return null;
		
		TokenGroup grp = new TokenGroup();
	    for(int i = ix;i < ix + cnt;i++)	
	    {
	    	grp.addTokensItem(tokens.get(i));
	    }	   
	    
	    if(matcher.isGroup(grp)) 
	    {
	    	grp.setTranslate(true);
	    	return grp;
	    }

	    if(cnt == 1)
	    {
	    	grp.setTranslate(false);
	    	return grp;
	    }
	    return null;
	}

}
