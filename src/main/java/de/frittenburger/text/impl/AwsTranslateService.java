package de.frittenburger.text.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.frittenburger.text.interfaces.TranslateCache;
import de.frittenburger.text.interfaces.TranslateService;
import de.frittenburger.text.model.Translation;

public class AwsTranslateService implements TranslateService {

	private final Logger logger = LogManager.getLogger(AwsTranslateService.class);

	private final AmazonTranslate translate;
	private final String toLang;
	private final String fromLang;
	private final TranslateCache cache;
	
	public  AwsTranslateService(String fromLang, String toLang,TranslateCache cache) throws IOException
	{
		this.fromLang = fromLang;
		this.toLang = toLang;
		this.cache = cache;
		Map<String,String> config = new ObjectMapper().readValue(new File("config/aws.json"),new TypeReference<HashMap<String,String>>() {});
		
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(config.get("accessKeyId"), config.get("secretAccessKey"));
       
        translate = AmazonTranslateClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withRegion(config.get("region"))
                .build();

	}
	@Override
	public Translation translate(String text) {
		
		
		String translatedText = cache.get(text);
		
		if(translatedText == null)
		{
		
		    TranslateTextRequest request = new TranslateTextRequest()
	        .withText(text)
	        .withSourceLanguageCode(fromLang)
	        .withTargetLanguageCode(toLang);
	
			TranslateTextResult result  = translate.translateText(request);
			
			if(!result.getSourceLanguageCode().equals(fromLang))
			{
				logger.error("SourceLanguage changed from {} to {}",fromLang,result.getSourceLanguageCode());
			}
			if(!result.getTargetLanguageCode().equals(toLang))
			{
				logger.error("TargetLanguage changed from {} to {}",toLang,result.getTargetLanguageCode());
			}
			translatedText = result.getTranslatedText();
			cache.add(text, translatedText);
		}
			
		Translation translation = new Translation();
		translation.setOrigin("AwsTranslateService");
		translation.setCandidates(Arrays.asList(translatedText));
		return translation;
		
				
	}

}
