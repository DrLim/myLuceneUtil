package org.lucene.field;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.lucene.exception.UnsupportedTypeValueException;

public final class FieldDefinition {
	
	private final String name;
	private final Object value;
	private final FieldType type;
	private final Field.Store sotre;
	private final boolean isMultipleQueryField;
	private final boolean isSingleQueryField;
	
	public FieldDefinition(String name, Object value,
			Store sotre, boolean isMultipleQueryField,
			boolean isSingleQueryField) throws UnsupportedTypeValueException {
		this.name = name;
		this.value = value;
		this.sotre = sotre;
		this.isMultipleQueryField = isMultipleQueryField;
		this.isSingleQueryField = isSingleQueryField;
		this.type = FieldType.getFieldType(this.value.getClass());
		if(null == this.type){
			throw new UnsupportedTypeValueException();
		}
	}
	
	public String getName() {
		return name;
	}
	public Object getValue() {
		return value;
	}
	public Field.Store getSotre() {
		return sotre;
	}
	public boolean isMultipleQueryField() {
		return isMultipleQueryField;
	}
	public boolean isSingleQueryField() {
		return isSingleQueryField;
	}
	public FieldType getType() {
		return type;
	}

}
