package org.lucene.field;

import java.io.File;

import org.lucene.exception.UnsupportedTypeValueException;

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
	
	public static FieldType getFieldType(Class<?> clazz) throws UnsupportedTypeValueException{
		String className = clazz.getName();
		try{
			return FieldType.valueOf(className.substring(className.lastIndexOf('.')+1).toUpperCase());
		}catch(IllegalArgumentException e){
			throw new UnsupportedTypeValueException(e);
		}
	}

	public Class<?> getClazz() {
		return clazz;
	}
	
}
