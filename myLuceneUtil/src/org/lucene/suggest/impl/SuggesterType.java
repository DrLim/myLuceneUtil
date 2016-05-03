package org.lucene.suggest.impl;

import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.search.suggest.analyzing.FreeTextSuggester;
import org.apache.lucene.search.suggest.analyzing.FuzzySuggester;

public enum SuggesterType {
	
	FREE_TEXT(FreeTextSuggester.class),
	ANALYZING(AnalyzingSuggester.class),
	ANALYZING_INFIX(AnalyzingInfixSuggester.class),
	FUZZY(FuzzySuggester.class);
	
	private Class<?> clazz ;
	
	private SuggesterType(Class<?> clazz){
		this.clazz = clazz;
	}

	public Class<?> getClazz() {
		return clazz;
	}

}
