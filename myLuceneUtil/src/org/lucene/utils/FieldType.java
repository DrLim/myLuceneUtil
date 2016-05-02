package org.lucene.utils;

public enum FieldType {
	
	STRING("string"),FILE("file"),INTEGER("int"),FLOAT("float"),LONG("long"),DOUBLE("double") ;
	
	private String value ;
	
	private FieldType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}
