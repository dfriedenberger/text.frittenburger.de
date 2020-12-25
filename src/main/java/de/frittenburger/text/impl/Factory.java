package de.frittenburger.text.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.text.interfaces.WordFrequencyService;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class Factory {


	public static WordFrequencyService getWordFrequencyServiceInstance(String language) throws IOException 
	{
		
		ClassLoader cl = Factory.class.getClassLoader();
		
		String path = language+"/frequency.txt";
		try(InputStream is = cl.getResourceAsStream(path))
		{
			if(is == null) throw new IOException(path + " not found");
			return new WordFrequencyServiceImpl(is);
		}
		
	}

	
	public static StanfordCoreNLP getPipelineInstance(String language) throws IOException 
	{
	
		Properties properties = new Properties();
		ClassLoader cl = Factory.class.getClassLoader();
		
		String path = "StanfordCoreNLP-"+language+".properties";

		if(language.equals("english")) // is default
			path = "StanfordCoreNLP.properties";
		
		try(InputStream is = cl.getResourceAsStream(path))
		{
			if(is == null) throw new IOException(path + " not found");
	    	properties.load(is);
		}
    	
		
    	// build pipeline
    	StanfordCoreNLP pipeline = new StanfordCoreNLP(properties);
		return pipeline;
		
		
	}

	
}
