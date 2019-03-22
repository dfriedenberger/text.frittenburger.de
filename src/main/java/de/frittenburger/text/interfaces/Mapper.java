package de.frittenburger.text.interfaces;

public interface Mapper<TSource, TTarget> {
	
	
	TTarget map(TSource source);

}
