package de.frittenburger.text.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import de.frittenburger.text.interfaces.Reader;
import de.frittenburger.text.interfaces.TranslateService;
import de.frittenburger.text.model.Translation;

public class DictTranslateService implements TranslateService {


	private final Map<String,String[]> map;
	public DictTranslateService(String filename) throws IOException
	{
		Reader<Map<String,String[]>> reader = new DictReader(filename);
		map = reader.read();
	}
	
	@Override
	public Translation translate(String text) {
		
		if(map.containsKey(text))
		{
			Translation translation = new Translation();
			translation.setOrigin("DictTranslateService");
			translation.setCandidates(Arrays.asList(map.get(text)));
			return translation;
		}
		return null;
	}

}
