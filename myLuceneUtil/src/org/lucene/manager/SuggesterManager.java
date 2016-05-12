package org.lucene.manager;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.search.suggest.analyzing.FreeTextSuggester;
import org.apache.lucene.search.suggest.analyzing.FuzzySuggester;
import org.lucene.suggest.Suggester;
import org.lucene.suggest.impl.SuggesterImpl;
import org.lucene.suggest.impl.SuggesterType;

public class SuggesterManager {

	public static Lookup getSuggesterAnalyzer(SuggesterType type,Analyzer analyzer){
		switch(type){
		case ANALYZING :
			return new AnalyzingSuggester(analyzer);
		case FREE_TEXT :
			return new FreeTextSuggester(analyzer);
		case FUZZY :
			return new FuzzySuggester(analyzer);
		default : return null;
		}
	}
	
	public static Suggester getSuggester(String indexDirectoryPath,Analyzer analyzer,SuggesterType suggesterType) throws IOException{
		return new SuggesterImpl(indexDirectoryPath, analyzer, suggesterType);
	}
	
}
