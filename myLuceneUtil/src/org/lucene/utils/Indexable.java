package org.lucene.utils;

import java.util.List;

@FunctionalInterface
public interface Indexable {

	public List<FieldDefinition> getFields(); 
	
}
