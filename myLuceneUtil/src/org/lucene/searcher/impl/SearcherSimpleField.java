package org.lucene.searcher.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.lucene.exception.SearcherException;
import org.lucene.searcher.Searcher;

public class SearcherSimpleField extends AbstractSearcher implements Searcher{

	private static final Logger LOGGER = Logger.getLogger(SearcherSimpleField.class);
	
	private QueryParser queryParser ;

	public SearcherSimpleField(String indexDirectoryPath,Analyzer analyzer,String field) throws IOException{
		super(indexDirectoryPath,analyzer);
		queryParser = new QueryParser(field,this.analyzer);
	}
	
	@Override
	public Map<String, Object> search(String textQuery) throws SearcherException {
		Map<String,Object> infos = new HashMap<>();
		List<Document> results = new ArrayList<>();
       	try {
			Query query = this.queryParser.parse(textQuery);
	        TopDocs topDocs = this.indexSearcher.search(query,10);
	        long time2 = Calendar.getInstance().getTimeInMillis();
	        infos.put(TOTAL_HITS, topDocs.totalHits);
	        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {  
	        		Document document = indexSearcher.doc(scoreDoc.doc);
	        		results.add(document);
	        		LOGGER.info("Result : "+document.get(queryParser.getField()));
				  
	        }
	        infos.put(RESULTS, results);
       	} catch (IOException | ParseException e) {
			LOGGER.error(e);
			throw new SearcherException();
		} 
        return infos;
	}

	public QueryParser getQueryParser() {
		return queryParser;
	}

	public void setQueryParser(QueryParser queryParser) {
		this.queryParser = queryParser;
	}
	
}
