package org.lucene.manager;

import org.apache.lucene.analysis.Analyzer;
import org.lucene.indexer.Indexer;
import org.lucene.indexer.impl.IndexerImpl;


public class IndexerManager {
	
	private IndexerManager(){}

	public static Indexer getIndexer(String indexDirectoryPath , Analyzer analyzer , boolean rebuildIndex){
		return new IndexerImpl(indexDirectoryPath, analyzer, rebuildIndex);
	}

}
