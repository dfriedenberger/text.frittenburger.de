package de.frittenburger.text.interfaces;

import java.io.IOException;


public interface Reader<T> {

	T read() throws IOException;

}
