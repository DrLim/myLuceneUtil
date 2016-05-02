package org.lucene.utils;

import java.io.File;

public enum FieldType {
	
	STRING(String.class),
	FILE(File.class),
	INTEGER(Integer.class),
	FLOAT(Float.class),
	LONG(Long.class),
	DOUBLE(Double.class) ;
	
	private Class<?> clazz;
	
	private FieldType(Class<?> clazz) {
		this.clazz = clazz ;
	}
	
	public static FieldType getFieldType(Class<?> clazz){
		String className = clazz.getName();
		return FieldType.valueOf(className.substring(className.lastIndexOf('.')+1).toUpperCase());
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
}
