package org.lucene.indexer;

import java.util.List;

import org.lucene.field.FieldDefinition;

@FunctionalInterface
public interface Indexable {

	public List<FieldDefinition> getFields(); 
	
}
