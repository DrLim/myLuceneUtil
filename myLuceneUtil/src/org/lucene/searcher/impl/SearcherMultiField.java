package org.lucene.searcher.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.lucene.exception.SearcherException;
import org.lucene.searcher.Searcher;

public class SearcherMultiField extends AbstractSearcher implements Searcher {
	
	private static final Logger LOGGER = Logger.getLogger(SearcherMultiField.class);
	
	private MultiFieldQueryParser multiFieldQueryParser ;
	
	public SearcherMultiField(String indexDirectoryPath,Analyzer analyzer,String...fields) throws IOException{
		super(indexDirectoryPath,analyzer);
		multiFieldQueryParser = new MultiFieldQueryParser(fields,this.analyzer);
		multiFieldQueryParser.setAllowLeadingWildcard(Boolean.TRUE);
	}

	public MultiFieldQueryParser getMultiFieldQueryParser() {
		return multiFieldQueryParser;
	}

	public void setMultiFieldQueryParser(MultiFieldQueryParser multiFieldQueryParser) {
		this.multiFieldQueryParser = multiFieldQueryParser;
	}

	@Override
	public Map<String,Object> search(String textQuery) throws SearcherException {
		Map<String,Object> infos = new HashMap<>();
		List<Document> results = new ArrayList<>();
       	try {
       		long startTime = System.currentTimeMillis();
			Query query = this.multiFieldQueryParser.parse(textQuery);
	        TopDocs topDocs = this.indexSearcher.search(query,10);
	        infos.put(TOTAL_HITS, topDocs.totalHits);
	        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {  
	        		Document document = indexSearcher.doc(scoreDoc.doc);
	        		results.add(document);
	        }
	        infos.put(RESULTS, results);
	        long endTime = System.currentTimeMillis();
	        infos.put(Searcher.DURATION,((double)(endTime-startTime))/1000);
       	} catch (IOException | ParseException e) {
			LOGGER.error(e);
			throw new SearcherException();
		} 
        return infos;
	}

}
