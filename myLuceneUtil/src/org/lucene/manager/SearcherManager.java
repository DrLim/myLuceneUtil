package org.lucene.manager;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.lucene.searcher.Searcher;
import org.lucene.searcher.impl.SearcherMultiField;
import org.lucene.searcher.impl.SearcherSimpleField;

public class SearcherManager {

	private SearcherManager(){}

	public static Searcher getSearcher(String indexDirectoryPath , Analyzer analyzer , String field) throws IOException{
		return new SearcherSimpleField(indexDirectoryPath, analyzer, field);
	}
	
	public static Searcher getSearcher(String indexDirectoryPath , Analyzer analyzer , String[] fields) throws IOException{
		return new SearcherMultiField(indexDirectoryPath, analyzer, fields);
	}
}
