package de.frittenburger.text.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;




import de.frittenburger.text.interfaces.TranslateService;
import de.frittenburger.text.model.TokenGroup;
import de.frittenburger.text.model.Translation;

public class TranslateProcessorTest {

	@Test
	public void test() throws IOException {
		
		
		TranslateProcessor translateProcessor = new TranslateProcessor(new TranslateService(){

			@Override
			public Translation translate(String text) {
				
				if(text.indexOf(' ') > 0)
				{
					Translation tr = new Translation();
					tr.setOrigin("service1");
					return tr;
				}
				return null;
			}},new TranslateProcessor(new TranslateService(){

				@Override
				public Translation translate(String text) {
					Translation tr = new Translation();
					tr.setOrigin("service2");
					return tr;				
				}}));
		
		List<TokenGroup> groups = TestBed.getList("tokengroups.de.json",TokenGroup.class);
	
		List<Translation> translations = groups.stream().map(translateProcessor).collect(Collectors.toList());
		
		assertEquals(26, translations.size());
		assertEquals(2, translations.stream().filter(t -> t != null && t.getOrigin().equals("service1")).count());
		assertEquals(2, translations.stream().filter(t -> t != null && t.getOrigin().equals("service2")).count());

	}

}
