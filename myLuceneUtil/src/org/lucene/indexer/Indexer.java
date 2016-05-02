package org.lucene.indexer;

import org.apache.lucene.document.Document;
import org.lucene.exception.DocumentNotIndexedException;
import org.lucene.utils.Indexable;

public interface Indexer {

	Document getDocument(Indexable indexable)
			throws DocumentNotIndexedException;
	
	void indexDocument(Indexable document) throws DocumentNotIndexedException ;

}
