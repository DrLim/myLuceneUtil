package org.lucene.indexer;

import java.util.Map;

import org.lucene.utils.FieldType;

public interface Indexable {

	public Map<String,Object> getFieldsValue();
	public Map<String,Boolean> getStoredFields();
	public Map<String,FieldType> getSearchField(); 
	
}
