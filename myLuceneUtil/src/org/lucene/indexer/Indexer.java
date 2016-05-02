package org.lucene.indexer;

import org.apache.lucene.document.Document;
import org.lucene.exception.DocumentNotIndexedException;

public interface Indexer {

	Document getDocument(Indexable indexable)
			throws DocumentNotIndexedException;
	
	void indexDocument(Indexable document) throws DocumentNotIndexedException ;

}
