package de.frittenburger.text.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.text.interfaces.TextService;
import de.frittenburger.text.model.Sentence;
import de.frittenburger.text.model.Text;

public class TextServiceImplTest {

	
	private static TextService serviceEs = null;
	private static TextService serviceDe = null;
	private static TextService serviceEn = null;

	@Before
	public void init() throws IOException
	{
		serviceEs = new TextServiceImpl("spanish");
		serviceDe = new TextServiceImpl("german");
		serviceEn = new TextServiceImpl("english");
	}
	
	
	
	public void runOneSentence(TextService service,String textstr, String ner1,String pos1) throws IOException {
			
		
        Text text = service.tokenize(textstr);
		assertNotNull(text);
		
		List<Sentence> sentences = text.getSentences();
		assertEquals(1,sentences.size());
		
		Sentence sentence = sentences.get(0);
		ObjectMapper mapper = new ObjectMapper();
		
		
		String ner2 = sentence.getTokens().stream().map(t -> t.getNamedEntity()).collect(Collectors.joining(","));
		String pos2 = sentence.getTokens().stream().map(t -> t.getPartOfSpeech()).collect(Collectors.joining(","));

		assertEquals(ner1,ner2);
		assertEquals(pos1,pos2);
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sentence));
		
	}


	@Test
	public void testSimpleSentenceDe() throws IOException {
		
		String ner = "O,O,O,PERSON,O,LOCATION,O";
		String pos = "pronoun,verb,adposition,proper-noun,adposition,proper-noun,punctuation";
		runOneSentence(serviceDe,"Ich wohne mit Georg in London.",ner,pos);

	}
	
	@Test
	public void testSimpleSentenceEs() throws IOException {
		
		String ner = "O,O,PERSON,O,CITY,O";
		String pos = "verb,adposition,proper-noun,adposition,proper-noun,punctuation";
		runOneSentence(serviceEs,"Vivo con Georg en Londres.",ner,pos);

	}
	
	@Test
	public void testSimpleSentenceEn() throws IOException {
		
		String ner = "O,O,O,PERSON,O,CITY,O";
		String pos = "pronoun,verb,preposition/subordinating-conjunction,proper-noun,preposition/subordinating-conjunction,proper-noun,punctuation";
		//String pos = "pronoun,verb,adposition,proper-noun,adposition,proper-noun,punctuation";
		runOneSentence(serviceEn,"I live with Georg in London.",ner,pos);

	}
	
	@Test
	public void testSimpleQuestionDe() throws IOException {
		
		String ner = "O,O,O,O,O";
		String pos = "adverb,auxiliary-verb,determiner,noun,punctuation";
		runOneSentence(serviceDe,"Wo ist das Auto?",ner,pos);

	}
	
	@Test
	public void testSimpleQuestionEs() throws IOException {
		
		String ner = "O,O,O,O,O,O";
		String pos = "punctuation,pronoun,verb,determiner,noun,punctuation";
		runOneSentence(serviceEs,"¿Dónde está el coche?",ner,pos);

	}
	
	@Test
	public void testSimpleQuestionEn() throws IOException {
		
		String ner = "O,O,O,O,O";
		String pos = "adverb,verb,determiner,noun,punctuation";
		runOneSentence(serviceEn,"Where is the car?",ner,pos);

	}
	
	@Test
	public void testEs() throws IOException {
		
		

		
		
        String textstr = "Es mi madre, Vu, yo soy Sue y él, mi hermano, Thao. Hemos vivido al lado. ¿Y? Thao quiere decirle algo.";

		Text text = serviceEs.tokenize(textstr);
		assertNotNull(text);
		List<Sentence> sentences = text.getSentences();
		assertEquals(4,sentences.size());
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(text));
		
		
	}
	
	
	
	
	@Test
	public void testEn() throws IOException {
		
	
		
        String textstr = "I am cold. Still with us, Brett? Right. Oh, I feel dead. Anybody ever tell you you look dead, man?";

		Text text = serviceEn.tokenize(textstr);
		assertNotNull(text);
		List<Sentence> sentences = text.getSentences();
		assertEquals(5,sentences.size());
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(text));
		
		
	}
}
