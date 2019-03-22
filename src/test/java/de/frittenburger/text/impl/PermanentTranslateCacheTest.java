package de.frittenburger.text.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.frittenburger.text.interfaces.TranslateCache;

public class PermanentTranslateCacheTest {

	@Rule
    public TemporaryFolder folder= new TemporaryFolder();

   
	@Test
	public void test() throws IOException {
		
		File createdFile= folder.newFile();
		TranslateCache cache = new PermanentTranslateCache(createdFile.getPath());
		
		
		assertNull(cache.get("Test"));
		cache.add("Test","Hello");
		assertEquals("Hello",cache.get("Test"));
		assertNull(cache.get("test"));
		
		assertEquals("Test::Hello\r\n".length(), createdFile.length());

	}

}
