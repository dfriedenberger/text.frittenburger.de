package de.frittenburger.text.app;

import static spark.Spark.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.text.impl.AwsTranslateService;
import de.frittenburger.text.impl.DictTranslateService;
import de.frittenburger.text.impl.GroupMatcherImpl;
import de.frittenburger.text.impl.PermanentTranslateCache;
import de.frittenburger.text.impl.TextServiceImpl;
import de.frittenburger.text.impl.TranslateProcessor;
import de.frittenburger.text.impl.VocabularyServiceImpl;
import de.frittenburger.text.interfaces.TextService;
import de.frittenburger.text.interfaces.TranslateCache;
import de.frittenburger.text.interfaces.TranslateService;
import de.frittenburger.text.interfaces.VocabularyService;
import de.frittenburger.text.model.Text;
import de.frittenburger.text.model.TokenGroup;
import de.frittenburger.text.model.Translation;

public class WebService {

    private static class ServiceCache 
    {
    	public TextService textService;
    	public VocabularyService vocabularyService;
    	public Map<String,TranslateProcessor> translateProcessorMap = new HashMap<String,TranslateProcessor>();
    }
    
    
    private static void createService(Map<String, ServiceCache> servicecaches, String sourceLanguage, String[] targetLanguages) throws IOException {
		
    	ServiceCache serviceCache = new ServiceCache();
    	servicecaches.put(sourceLanguage, serviceCache);
    	
    	serviceCache.textService = new TextServiceImpl(sourceLanguage);
    	serviceCache.vocabularyService = new VocabularyServiceImpl(new GroupMatcherImpl(sourceLanguage +"/groups.txt"));
    	for(String targetLanguage : targetLanguages)
    	{
    		TranslateService dictTranslateService = new DictTranslateService("dictionary/dict-"+sourceLanguage+"-"+targetLanguage+".txt");
			TranslateCache cache = new PermanentTranslateCache("cache/cache-"+sourceLanguage+"-"+targetLanguage+".txt");
			TranslateService awsTranslateService = new AwsTranslateService(sourceLanguage,targetLanguage,cache);
			
			//build chain
			TranslateProcessor translateProcessor = new TranslateProcessor(dictTranslateService , new TranslateProcessor(awsTranslateService));		 
			serviceCache.translateProcessorMap.put(targetLanguage, translateProcessor);
    	}
    	
	}
	
	public static void main(String[] args) throws IOException {
		
		
		Map<String,ServiceCache> servicecaches = new HashMap<String,ServiceCache>();
		
		//init
        createService(servicecaches,"de",new String[]{"es"});
        createService(servicecaches,"es",new String[]{"de"});
		
		 staticFileLocation("/htdocs");
         
		
		 
		 post("/translate",(request, response) -> {
			 

			    String bearerToken = null;
				String auth = request.headers("Authorization");
				if(auth != null && auth.startsWith("Bearer")) {
					bearerToken = auth.substring("Bearer".length()).trim();
				}
			 
				if(bearerToken == null || bearerToken.isEmpty())
				{
					response.header("WWW-Authenticate", "Bearer");
			        halt(401, "You need a Bearer token");
				}
			 
			 
			    response.type("application/json");
			    Map<String,Object> data = new HashMap<String,Object>();
			    
			    String textstr = request.queryParams("text");
			    String sourceLanguage = request.queryParams("sourcelanguage");
			    String targetLanguage = request.queryParams("targetlanguage");
			    
			    if(!servicecaches.containsKey(sourceLanguage))
			        halt(404, "source language "+sourceLanguage+" not found");
			    ServiceCache services = servicecaches.get(sourceLanguage);
			    	
			    if(!services.translateProcessorMap.containsKey(targetLanguage))
			        halt(404, "target language "+targetLanguage+" not found");
			   
				Text text = services.textService.tokenize(textstr);
				List<TokenGroup> groups = services.vocabularyService.getVocabulary(text);
				
				data.put("groups",groups);

			
				List<Translation> translation = groups.stream().map(services.translateProcessorMap.get(targetLanguage)).collect(Collectors.toList());
				data.put("translation",translation);

			    
			    return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data);
		 });
		 
		 
		 System.out.println("ready");
	}


	

}
