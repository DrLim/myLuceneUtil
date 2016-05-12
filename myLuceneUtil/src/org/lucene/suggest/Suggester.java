package org.lucene.suggest;

import java.util.Set;

import org.lucene.exception.SuggestException;

public interface Suggester {

	Set<String> getSuggestions(CharSequence term, String[] fields) throws SuggestException;
	Set<String> getSuggestions(CharSequence term, String field) throws SuggestException;
	
}
