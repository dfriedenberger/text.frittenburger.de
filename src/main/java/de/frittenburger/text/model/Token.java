package de.frittenburger.text.model;

public class Token {

	private String text;
	private String namedEntity;
	private String partOfSpeech;
	private int level;
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getNamedEntity() {
		return namedEntity;
	}

	public void setNamedEntity(String namedEntity) {
		this.namedEntity = namedEntity;
	}

	public String getPartOfSpeech() {
		return partOfSpeech;
	}

	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Token [text=" + text + ", namedEntity=" + namedEntity
				+ ", partOfSpeech=" + partOfSpeech + ", level=" + level + "]";
	}



}
