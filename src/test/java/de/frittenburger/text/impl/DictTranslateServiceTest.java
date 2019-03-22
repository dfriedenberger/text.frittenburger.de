package de.frittenburger.text.impl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.Test;

import de.frittenburger.text.interfaces.TranslateService;
import de.frittenburger.text.model.TokenGroup;
import de.frittenburger.text.model.Translation;

public class DictTranslateServiceTest {

	@Test
	public void test() throws IOException {
		
		TranslateService service = new DictTranslateService("dictionary/dict-de-es.txt");
		TranslateProcessor translateProcessor = new TranslateProcessor(service);

		List<TokenGroup> groups = TestBed.getList("tokengroups.de.json",TokenGroup.class);
		
		List<Translation> translations = groups.stream().map(translateProcessor).collect(Collectors.toList());
		
		assertEquals(26, translations.size());
		assertEquals(1, translations.stream().filter(Objects::nonNull).count());

		
	}

}
