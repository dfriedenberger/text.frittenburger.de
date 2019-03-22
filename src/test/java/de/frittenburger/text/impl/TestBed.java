package de.frittenburger.text.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


public class TestBed {

	public static <T> List<T> getList(String resourceFilename,	Class<T> clazz) throws IOException {
		try(InputStream is = TestBed.class.getClassLoader().getResourceAsStream(resourceFilename))
		{
			List<T> list = new ArrayList<T>();
			JsonNode tree = new ObjectMapper().readTree(is);
			
			ArrayNode array = (ArrayNode)tree;
			for(JsonNode node : array)
			{
				T elm = new ObjectMapper().convertValue(node, clazz);
				list.add(elm);
			}
			
			return list;
		}
	}

	public static <T> T get(String resourceFilename, Class<T> clazz) throws IOException {
		try(InputStream is = TestBed.class.getClassLoader().getResourceAsStream(resourceFilename))
		{
			return new ObjectMapper().readValue(is, clazz);
		}
	}

}
