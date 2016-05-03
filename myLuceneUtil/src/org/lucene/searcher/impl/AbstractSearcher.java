package org.lucene.searcher.impl;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lucene.searcher.Searcher;

public abstract class AbstractSearcher implements Searcher {
	
	protected IndexSearcher indexSearcher;
	protected Analyzer analyzer;

	public AbstractSearcher(String indexDirectoryPath,Analyzer analyzer) throws IOException {
		Directory directory = FSDirectory.open(Paths.get(indexDirectoryPath));
		IndexReader indexReader = DirectoryReader.open(directory);
		indexSearcher = new IndexSearcher(indexReader);
		this.analyzer = analyzer ;
	}

	public IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}

	public void setIndexSearcher(IndexSearcher indexSearcher) {
		this.indexSearcher = indexSearcher;
	}
}
