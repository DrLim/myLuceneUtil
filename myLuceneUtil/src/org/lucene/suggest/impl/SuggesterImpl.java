package org.lucene.suggest.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lucene.exception.SuggestException;
import org.lucene.manager.SuggesterManager;
import org.lucene.suggest.Suggester;

public final class SuggesterImpl implements Suggester {

	private static final Logger LOGGER = Logger.getLogger(SuggesterImpl.class);

	private final IndexReader indexReader;
	private final Analyzer analyzer;
	private final SuggesterType suggesterType;

	public SuggesterImpl(String indexDirectoryPath, Analyzer analyzer, SuggesterType suggesterType) throws IOException {
		Directory directory = FSDirectory.open(Paths.get(indexDirectoryPath));
		indexReader = DirectoryReader.open(directory);
		this.analyzer = analyzer;
		this.suggesterType = suggesterType;
	}

	@Override
	public Set<String> getSuggestions(CharSequence term, String[] fields) throws SuggestException {
		Set<String> results = new HashSet<>();
		for (String field : fields) {
			results.addAll(getSuggestions(term, field));
		}
		return results;
	}

	@Override
	public Set<String> getSuggestions(CharSequence term, String field) throws SuggestException {
		try {
			LuceneDictionary dictionary = new LuceneDictionary(indexReader, field);
			Lookup suggester = SuggesterManager.getSuggesterAnalyzer(suggesterType, analyzer);
			suggester.build(dictionary);

			List<Lookup.LookupResult> lookupResultListF = suggester.lookup(term, false, 100);
			Set<String> results = new HashSet<>();
			for (Lookup.LookupResult lookupResult : lookupResultListF) {
				results.add(lookupResult.key.toString());
			}
			return results;
		} catch (SecurityException | IllegalArgumentException | IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw new SuggestException(e.getMessage(), e);
		}
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public SuggesterType getSuggesterType() {
		return suggesterType;
	}

}
