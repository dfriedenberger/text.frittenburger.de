package de.frittenburger.text.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.frittenburger.text.interfaces.AnnotationWrapper;
import de.frittenburger.text.interfaces.TextService;
import de.frittenburger.text.interfaces.WordFrequencyService;
import de.frittenburger.text.model.Sentence;
import de.frittenburger.text.model.Text;
import de.frittenburger.text.model.Token;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class TextServiceImpl implements TextService {

	
	
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(TextServiceImpl.class);
	

	private final StanfordCoreNLP pipeline;
	private final WordFrequencyService frequencyService;
	private final AnnotationWrapper wrapper;
	public TextServiceImpl(String language) throws IOException {
		
		this.frequencyService = Factory.getWordFrequencyServiceInstance(language);
		this.pipeline = Factory.getPipelineInstance(language);
		this.wrapper = Factory.getAnnotationWrapperInstance(language);
	}

	@Override
	public Text tokenize(String textstr) throws IOException {

		
	    // create an empty Annotation just with the given text
        Annotation document = new Annotation(textstr);

        // run all Annotators on this text
        pipeline.annotate(document);
		
		
		Text text = new Text();
		text.setSentences(new ArrayList<Sentence>()); 
		 
		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);

		for (CoreMap sent : sentences) {

			Sentence sentence = new Sentence();
			text.addSentencesItem(sentence);

			for (CoreLabel tok : sent.get(TokensAnnotation.class)) {

				Token token = new Token();
				sentence.addTokensItem(token);

				// this is the text of the token
				String word = tok.get(TextAnnotation.class);
				token.setText(word);
				
				// this is the POS tag of the token
				String pos = tok.get(PartOfSpeechAnnotation.class);
				token.setPartOfSpeech(pos);
				
			
				// this is the NER label of the token
				String ne = tok.get(NamedEntityTagAnnotation.class);
				token.setNamedEntity(ne);

				token.setLevel(-1);
				if(wrapper.isVocabulary(pos,ne))
				{
					int level = frequencyService.level(word);
					token.setLevel(level);
				}
			   
				//System.out.println(word + " " + pos + " " + ne);

			}
			
		}
		
		return text;
	}

	

}
