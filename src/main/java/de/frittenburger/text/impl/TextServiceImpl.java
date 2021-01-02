package de.frittenburger.text.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	private final StanfordCoreNLP pipeline;
	private final WordFrequencyService frequencyService;
	public TextServiceImpl(String language) throws IOException {
		
		this.frequencyService = Factory.getWordFrequencyServiceInstance(language);
		this.pipeline = Factory.getPipelineInstance(language);
		
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

			sentence.setText(sent.get(TextAnnotation.class));

			for (CoreLabel tok : sent.get(TokensAnnotation.class)) {

				Token token = new Token();
				sentence.addTokensItem(token);

				// this is the text of the token
				String word = tok.get(TextAnnotation.class);
				token.setText(word);
				
				// this is the POS tag of the token
				String pos = tok.get(PartOfSpeechAnnotation.class);
				String partofSpeech = pos2class(pos);
				token.setPartOfSpeech(partofSpeech);
				
			
				// this is the NER label of the token
				String namedEntity = tok.get(NamedEntityTagAnnotation.class);
				token.setNamedEntity(namedEntity);

				token.setLevel(-1);
				if(isVocabulary(partofSpeech,namedEntity))
				{
					int level = frequencyService.level(word);
					token.setLevel(level);
				}
			   
			}

		}
		
		return text;
	}

	private boolean isVocabulary(String partofSpeech, String namedEntity) {

		
		if(partofSpeech.equals("adverb")) return true;
		if(partofSpeech.equals("adjective")) return true;
		if(partofSpeech.equals("noun")) return true;
		if(partofSpeech.equals("verb")) return true;
		
		if(partofSpeech.equals("punctuation")) return false;

		if(!namedEntity.equals("O")) return false;
		return true;
	}

	private String pos2class(String pos) {

		switch (pos) {
		// spanich and german
		case "ADJ":
		case "JJ":
		case "JJR": //comparative	'bigger'
		case "JJS": //superlative	'biggest'
			return "adjective";
		case "ADP":
			return "adposition";
		case "ADV":
		case "RB": //adverb	very, silently
		case "RBR": //adverb, comparative better
		case "RBS": //adverb, superlative best
		case "WRB": //wh-abverb	where, when
			return "adverb";
		case "AUX":
			return "auxiliary-verb";
		case "CCONJ":
		case "CC":
			return "coordinating-conjunction";
		case "DET":
		case "DT":
		case "WDT": //wh-determiner	which
			return "determiner";
		case "INTJ":
			return "interjection";
		case "NOUN":
		case "NN": //noun, singular 'desk'
		case "NNS": //noun plural	'desks'
			return "noun";
		case "NUM":
			return "numeral";
		case "PART":
			return "particle";
		case "PRON":
		case "PRP": //personal pronoun	I, he, she
		case "PRP$": //possessive pronoun	my, his, hers
		case "WP":	//wh-pronoun	who, what
		case "WP$": //	possessive wh-pronoun	whose
			return "pronoun";
		case "PROPN":
		case "NNP": //proper noun, singular	'Harrison'
		case "NNPS": //proper noun, plural	'Americans'
			return "proper-noun";
		case "PUNCT":
		case ".":
			return "punctuation";
		case "SCONJ":
			return "subordinating-conjunction";
		case "SYM":
			return "symbol";
		case "IN":
			return "preposition/subordinating-conjunction";
		case "VERB":
		case "VB":	//verb, base form	take
		case "VBD":	//verb, past tense	took
		case "VBG":	//verb, gerund/present participle	taking
		case "VBN":	//verb, past participle	taken
		case "VBP": //verb, sing. present, non-3d	take
		case "VBZ":	//verb, 3rd person sing. present	takes
			return "verb";
		default:
			return "#"+pos;
		}
	}

}
