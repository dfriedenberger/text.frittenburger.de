package de.frittenburger.text.impl;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import de.frittenburger.text.interfaces.GroupMatcher;
import de.frittenburger.text.model.Token;
import de.frittenburger.text.model.TokenGroup;

public class GroupMatcherImplTest {

	@Test
	public void test() throws IOException {
		GroupMatcher matcher = new GroupMatcherImpl("de/groups.txt");
		
		
		Token t1 = new Token();
		t1.setText("Wir");
		t1.setNamedEntity("O");
		t1.setPartOfSpeech("PPER");

		Token t2 = new Token();
		t2.setText("wohnen");
		t2.setNamedEntity("O");
		t2.setPartOfSpeech("VVFIN");
		
		TokenGroup grp1 = new TokenGroup();
		grp1.addTokensItem(t1);
		grp1.addTokensItem(t2);

		TokenGroup grp2 = new TokenGroup();
		grp2.addTokensItem(t2);
		grp2.addTokensItem(t1);
		
		assertTrue(matcher.isGroup(grp1));
		assertFalse(matcher.isGroup(grp2));

	}
	
	


}
