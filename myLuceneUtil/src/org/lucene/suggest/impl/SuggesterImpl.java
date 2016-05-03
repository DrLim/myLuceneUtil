package org.lucene.suggest.impl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.lucene.alalysis.CustomAnalyzer;
import org.lucene.suggest.Suggester;

public final class SuggesterImpl implements Suggester {

	private static final String SEPARATOR = ",";
	private final IndexReader indexReader;
	private final Analyzer analyzer;
	private final SuggesterType suggesterType;
	
	
	
	
	public SuggesterImpl(String indexDirectoryPath, Analyzer analyzer,SuggesterType suggesterType) throws IOException{
		Directory directory = FSDirectory.open(Paths.get(indexDirectoryPath));
		indexReader = DirectoryReader.open(directory);
		this.analyzer = analyzer ;
		this.suggesterType = suggesterType;
	}

	@Override
	public String[] getSuggestions(CharSequence term,String[] fields) {
		return null ;

	}

	@Override
	public String[] getSuggestions(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getSuggestions(CharSequence term, String field) {
		try {
			Constructor<?> ctor = suggesterType.getClazz().getConstructor(suggesterType.getClazz());
			Lookup suggester = (Lookup)(ctor.newInstance(new Object[] { analyzer }));
			List<Lookup.LookupResult> lookupResultListF = suggester.lookup(term, false, 100);
			StringBuilder sb = new StringBuilder();
			for (Lookup.LookupResult lookupResult : lookupResultListF) {
				sb.append(lookupResult.key);
				if(lookupResultListF.size()!=(lookupResultListF.indexOf(lookupResult)+1)){
					sb.append(SEPARATOR);
				}
			}
			return sb.toString().split(SEPARATOR);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public SuggesterType getSuggesterType() {
		return suggesterType;
	}

}
