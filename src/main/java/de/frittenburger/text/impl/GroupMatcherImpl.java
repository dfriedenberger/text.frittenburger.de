package de.frittenburger.text.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import de.frittenburger.text.interfaces.GroupMatcher;
import de.frittenburger.text.model.Token;
import de.frittenburger.text.model.TokenGroup;

public class GroupMatcherImpl implements GroupMatcher {

	
	private final List<TokenGroup> groups = new ArrayList<TokenGroup>();
	
	private final TokenGroupMatcher groupMatcher = new TokenGroupMatcher();
	public GroupMatcherImpl(String filename) throws IOException {
		
		try(
				InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
				BufferedReader in = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))
		   )
		{
			

			while(true)
			{
				String line = in.readLine();
				if(line == null) break;
				if(line.trim().equals("")) continue;
				if(line.trim().startsWith("#")) continue;
				
				TokenGroup grp = new TokenGroup();
				for(String tokenDef : line.trim().split("\\s+"))
				{
					String[] def = tokenDef.split(":");
					if(def.length < 2) throw new IOException(line);
					Token t = new Token();
					t.setPartOfSpeech(def[0].trim());
					t.setNamedEntity(def[1].trim());
					t.setText("*");
						
					grp.addTokensItem(t);
					
				}
				groups.add(grp);
			}
			
			
		}
	}

	@Override
	public boolean isGroup(TokenGroup currentGroup) {

		
		for(TokenGroup expectedGroup : groups)
		{
			if(groupMatcher.match(expectedGroup,currentGroup)) return true;
		}
		
		return false;
	}

}
