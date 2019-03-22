package de.frittenburger.text.impl;

import de.frittenburger.text.interfaces.AnnotationWrapper;

public class SpanishAnnotationWrapper implements AnnotationWrapper {

	@Override
	public boolean isVocabulary(String pos, String ne) {

		
		if(pos.equals("PUNCT")) return false;
		if(ne.equals("PERSON")) return false;
		
		return true;
	}

}
