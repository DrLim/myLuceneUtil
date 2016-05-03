package org.lucene.indexer;

import java.util.List;

import org.lucene.exception.DocumentNotIndexedException;
import org.lucene.utils.Indexable;

public interface Indexer {
	
	void indexDocument(Indexable indexable) throws DocumentNotIndexedException ;
	void indexDocuments(List<Indexable> indexables) throws DocumentNotIndexedException;

}
