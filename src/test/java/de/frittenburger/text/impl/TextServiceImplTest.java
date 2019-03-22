package de.frittenburger.text.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.text.interfaces.TextService;
import de.frittenburger.text.model.Sentence;
import de.frittenburger.text.model.Text;

public class TextServiceImplTest {

	@Test
	public void testDe() throws IOException {
		
		TextService serviceDe = new TextServiceImpl("de");
		
        String textstr = "Das ist meine Mutter Vu, ich heiße Sue und das ist mein Bruder Thao. Wir wohnen nebenan. Und? Thao möchte Ihnen was sagen.";

		Text text = serviceDe.tokenize(textstr);
		assertNotNull(text);
		
		List<Sentence> sentences = text.getSentences();
		assertEquals(4,sentences.size());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("tmp/text.de.json"),text);
		
		
	}

	
	@Test
	public void testEs() throws IOException {
		
		

		TextService serviceDe = new TextServiceImpl("es");
		
        String textstr = "Es mi madre, Vu, yo soy Sue y él, mi hermano, Thao. Hemos vivido al lado. ¿Y? Thao quiere decirle algo.";

		Text text = serviceDe.tokenize(textstr);
		assertNotNull(text);
		List<Sentence> sentences = text.getSentences();
		assertEquals(4,sentences.size());
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("tmp/text.es.json"),text);
		
		
	}
	
	
	
	
	@Test
	public void testEn() throws IOException {
		
	
		TextService serviceDe = new TextServiceImpl("en");
		
        String textstr = "I am cold. Still with us, Brett? Right. Oh, I feel dead. Anybody ever tell you you look dead, man?";

		Text text = serviceDe.tokenize(textstr);
		assertNotNull(text);
		List<Sentence> sentences = text.getSentences();
		assertEquals(5,sentences.size());
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("tmp/text.en.json"),text);
		
		
	}
}
