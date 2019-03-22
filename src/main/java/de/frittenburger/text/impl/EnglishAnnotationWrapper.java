package de.frittenburger.text.impl;

import de.frittenburger.text.interfaces.AnnotationWrapper;

public class EnglishAnnotationWrapper implements AnnotationWrapper {

	@Override
	public boolean isVocabulary(String pos, String ne) {

		
		if(pos.equals(".")) return false;
		if(pos.equals(",")) return false;
		if(ne.equals("PERSON")) return false;
		
		return true;
	}

}
