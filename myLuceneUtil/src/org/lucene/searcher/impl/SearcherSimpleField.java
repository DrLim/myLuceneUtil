package org.lucene.searcher.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		super(indexDirectoryPath);
		queryParser = new QueryParser(field,analyzer);
	}
	
	@Override
	public List<Document> search(String textQuery) throws SearcherException {
		List<Document> results = new ArrayList<>();
       	try {
			Query query = this.queryParser.parse(textQuery);
	        TopDocs topDocs = this.indexSearcher.search(query,10);
	        LOGGER.info("total hits : "+topDocs.totalHits);
	        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {  
	        		Document document = indexSearcher.doc(scoreDoc.doc);
	        		results.add(document);
	        		LOGGER.info("Result : "+document.get(queryParser.getField()));
				  
	        }
       	} catch (IOException | ParseException e) {
			LOGGER.error(e);
			throw new SearcherException();
		} 
        return results;
	}

	public QueryParser getQueryParser() {
		return queryParser;
	}

	public void setQueryParser(QueryParser queryParser) {
		this.queryParser = queryParser;
	}
	
}
