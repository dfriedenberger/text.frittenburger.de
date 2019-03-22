package de.frittenburger.text.impl;


import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;



import de.frittenburger.text.interfaces.TranslateCache;
import de.frittenburger.text.interfaces.TranslateService;
import de.frittenburger.text.model.TokenGroup;
import de.frittenburger.text.model.Translation;

public class AwsTranslateServiceTest {

	public static void main(String args[]) throws IOException {
	
		TranslateService service = new AwsTranslateService("de","es",new TranslateCache() {

			@Override
			public void add(String text, String translatedText) {
				System.out.println("add to cache: "+text+" = "+translatedText);
			}

			@Override
			public String get(String text) {
				if(text.equals("ich heiﬂe"))
					return "me llamo";
				if(text.equals("Bruder"))
					return "Hermano";
				
				return null;
			}});
		
		TranslateProcessor translateProcessor = new TranslateProcessor(service);
		List<TokenGroup> groups = TestBed.getList("tokengroups.de.json",TokenGroup.class);
		
		List<Translation> translations = groups.stream().map(translateProcessor).collect(Collectors.toList());
		
		

		translations.stream().filter(Objects::nonNull).forEach(translation -> System.out.println(translation));
		
	}

}
