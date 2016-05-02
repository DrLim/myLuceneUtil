package org.lucene.main;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.lucene.alalysis.CustomAnalyzer;
import org.lucene.exception.DocumentNotIndexedException;
import org.lucene.exception.SearcherException;
import org.lucene.indexer.Indexer;
import org.lucene.manager.IndexerManager;
import org.lucene.manager.SearcherManager;
import org.lucene.object.test.Book;
import org.lucene.searcher.Searcher;

public class Main {
	
	public static void main(String...args){
		Indexer indexer = IndexerManager.getIndexer("C:\\Users\\samia\\Desktop\\index", new CustomAnalyzer(), true);
		Book book = new Book(125,"Spring in action","C:\\Users\\samia\\Desktop\\Ali\\Ali M.docx");
		try {
			indexer.indexDocument(book);
		} catch (DocumentNotIndexedException e) {
			e.printStackTrace();
		}
		
		try{
			Searcher searcher = SearcherManager.getSearcher("C:\\Users\\samia\\Desktop\\index", new CustomAnalyzer(), new String[]{"title","content"});
			List<Document> results = searcher.search("spring");
			for(Document doc : results){
				System.out.println(doc.get("title"));
			}
		}catch(IOException | SearcherException e){
			e.printStackTrace();
		}
		
		
	}

}
