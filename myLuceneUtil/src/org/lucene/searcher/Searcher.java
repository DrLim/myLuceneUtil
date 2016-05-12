package org.lucene.searcher;

import java.util.Map;

import org.lucene.exception.SearcherException;

@FunctionalInterface
public interface Searcher {
	
	final String TOTAL_HITS = "totalHits";
	final String RESULTS = "results";
	final String DURATION = "duration";
	
	Map<String, Object> search(String textQuery) throws SearcherException;
}
