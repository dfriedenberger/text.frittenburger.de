package de.frittenburger.text.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;






import de.frittenburger.text.interfaces.Reader;

public class DictReader implements Reader<Map<String, String[]>> {

	private final String filename;

	public DictReader(String filename)  {
		
		this.filename = filename;
	
	
	}

	@Override
	public Map<String, String[]> read() throws IOException {
		
		try(
				InputStream is = new FileInputStream(filename);
				BufferedReader in = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))
		   )
		{
			
			Map<String, String[]> map = new HashMap<String, String[]>();
			while(true)
			{
				String line = in.readLine();
				if(line == null) break;
				if(line.trim().equals("")) continue;
				if(line.trim().startsWith("#")) continue;
				int i = line.indexOf("::");
				if(i < 0) continue;
				String key = line.substring(0, i).trim();
				String[] values = line.substring(i+2).trim().split(";");
				map.put(key, values);
			}
			
			return map;
		}	
		}

}
