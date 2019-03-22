package de.frittenburger.text.interfaces;

public interface TranslateCache {

	void add(String text, String translatedText);
	String get(String text);

}
