package de.frittenburger.text.interfaces;

import java.util.List;

import de.frittenburger.text.model.Text;
import de.frittenburger.text.model.TokenGroup;

public interface VocabularyService {

	List<TokenGroup> getVocabulary(Text text);

}
