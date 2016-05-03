package org.lucene.suggest;

public interface Suggester {

	String[] getSuggestions(String term);

	String[] getSuggestions(CharSequence term, String[] fields);
	String[] getSuggestions(CharSequence term, String field);
	
}
