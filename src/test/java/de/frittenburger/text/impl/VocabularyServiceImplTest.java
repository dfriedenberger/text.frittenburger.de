package de.frittenburger.text.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.text.interfaces.GroupMatcher;
import de.frittenburger.text.interfaces.VocabularyService;
import de.frittenburger.text.model.Text;
import de.frittenburger.text.model.TokenGroup;

public class VocabularyServiceImplTest {


	@Test
	public void test() throws IOException {
		
		GroupMatcher matcher = new GroupMatcher() {

			@Override
			public boolean isGroup(TokenGroup grp) {
				
			
				if(grp == null)
					throw new IllegalArgumentException("grp");

				switch(grp.getTokens().size())
				{
				case 0:
					throw new IllegalArgumentException("grp");
				case 1: //Single Vocabulary
					return grp.getTokens().get(0).getLevel() >= 0;
				case 2: //PPER + VVFIN
					return grp.getTokens().get(0).getPartOfSpeech().equals("PPER") 
							&& grp.getTokens().get(1).getPartOfSpeech().equals("VVFIN");
				case 3:
				case 4:
				case 5:
					return false;
				default:
					throw new RuntimeException("not implemented");
				}
				
			}
		};
		
		
		VocabularyService service = new VocabularyServiceImpl(matcher);

		Text text = TestBed.get("text.de.json",Text.class);
		
		

			
		List<TokenGroup> vocabulary = service.getVocabulary(text);
			
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("tmp/tokengroups.de.json"),vocabulary);
		
		assertEquals(23,vocabulary.size());
		assertEquals(28,vocabulary.stream().mapToInt(g -> g.getTokens().size()).sum());
		assertEquals(17,vocabulary.stream().filter(g -> g.getTranslate()).count());

			
		
	}

}
