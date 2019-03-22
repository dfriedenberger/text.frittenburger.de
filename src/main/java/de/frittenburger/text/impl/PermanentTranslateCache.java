package de.frittenburger.text.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.text.interfaces.Reader;
import de.frittenburger.text.interfaces.TranslateCache;

public class PermanentTranslateCache implements TranslateCache {

	private final Logger logger = LogManager.getLogger(AwsTranslateService.class);

	private final Map<String,String[]> map;
	private final String filename;
	
	public PermanentTranslateCache(String filename) throws IOException
	{
		Reader<Map<String,String[]>> reader = new DictReader(filename);

		this.filename = filename;
		this.map = reader.read();
	}
	
	@Override
	public String get(String text) {

		if(map.containsKey(text))
			return map.get(text)[0];
		return null;
	}
	
	@Override
	public void add(String text, String translatedText) {

		map.put(text,new String[]{translatedText});
		
		try(PrintWriter out = new PrintWriter(
			    new OutputStreamWriter(
			        new FileOutputStream(filename, true), // true to append
			        StandardCharsets.UTF_8                  // Set encoding
			    )
			))
			{
				out.println(text + "::" + translatedText);
				out.flush();
			}
		catch(IOException e)
		{
			logger.error(e);
		}

	}

	

}
