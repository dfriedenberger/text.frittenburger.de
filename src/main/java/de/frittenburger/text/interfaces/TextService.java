package de.frittenburger.text.interfaces;

import java.io.IOException;

import de.frittenburger.text.model.Text;


public interface TextService {

	Text tokenize(String textstr) throws IOException;

}
