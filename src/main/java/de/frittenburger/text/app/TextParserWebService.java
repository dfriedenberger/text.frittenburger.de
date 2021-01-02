package de.frittenburger.text.app;

import static spark.Spark.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.text.impl.Factory;
import de.frittenburger.text.impl.TextServiceImpl;
import de.frittenburger.text.interfaces.TextService;
import de.frittenburger.text.model.Text;

public class TextParserWebService {

	
	private static final Logger logger = LogManager.getLogger(TextParserWebService.class);

	public static void main(String[] args) throws IOException {
		
		 final Map<String,TextService> textServices = new HashMap<>();
		//init
    	textServices.put("english",new TextServiceImpl("english"));
    	logger.info("english pipeline loaded");
    	textServices.put("german",new TextServiceImpl("german"));
    	logger.info("spanish pipeline loaded");
    	textServices.put("spanish",new TextServiceImpl("spanish"));
    	logger.info("spanish pipeline loaded");

		staticFileLocation("/htdocs");
         
		

		 
		 post("/parse",(request, response) -> {
			 

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
			    String language = request.queryParams("language");

			    
			    if(!textServices.containsKey(language))
				{
			        halt(404, "Language not supported");
				}
			    
		    	
		    	TextService textService = textServices.get(language);
		    	
		    	Text text = textService.tokenize(textstr);
		    	data.put("text",text);
		    	data.put("status", "OK");
			    
			 			
			    return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data);
		 });
		 
		 
		 logger.info("ready, start on "+port());
	}


	

}
