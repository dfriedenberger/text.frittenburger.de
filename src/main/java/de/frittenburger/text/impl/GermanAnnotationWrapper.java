package de.frittenburger.text.impl;

import de.frittenburger.text.interfaces.AnnotationWrapper;

public class GermanAnnotationWrapper implements	AnnotationWrapper {

	@Override
	public boolean isVocabulary(String pos, String ne) {

		//Punctation
		if(pos.startsWith("$")) return false; 
		
		//Named Entity
		if(pos.equals("NE")) return false;

		
		
		return true;
	}

}
