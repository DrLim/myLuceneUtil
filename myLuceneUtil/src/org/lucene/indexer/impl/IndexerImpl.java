package org.lucene.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.lucene.exception.DocumentNotIndexedException;
import org.lucene.exception.UnsupportedTypeValueException;
import org.lucene.indexer.Indexable;
import org.lucene.indexer.Indexer;
import org.xml.sax.SAXException;

public final class IndexerImpl implements Indexer {

	private static final Logger LOGGER = Logger.getLogger(IndexerImpl.class);

	private IndexWriter writer;

	public IndexerImpl(String indexDirectoryPath, Analyzer analyzer , boolean rebuildIndex) {
		try {
			Directory indexDirectory = FSDirectory.open(Paths
					.get(indexDirectoryPath));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			config.setOpenMode(rebuildIndex?OpenMode.CREATE : OpenMode.APPEND);
			writer = new IndexWriter(indexDirectory, config);
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}

	@Override
	public Document getDocument(Indexable indexable)
			throws DocumentNotIndexedException {
		LOGGER.info("Start indexing document : " + indexable);
		Document document = new Document();
		Iterator<Entry<String, Object>> it = indexable.getFieldsValue()
				.entrySet().iterator();
		Map<String, Boolean> isStoredField = indexable.getStoredFields();
		while (it.hasNext()) {
			Map.Entry<String, Object> pair = it.next();
			String fieldName = pair.getKey();
			Object fieldValue = pair.getValue();
			Field.Store store = isStoredField.containsKey(pair.getKey()) ? (isStoredField
					.get(pair.getKey()) ? Field.Store.YES : Field.Store.NO)
					: Field.Store.NO;
			LOGGER.info(pair.getKey() + " = " + pair.getValue());
			Field field = createField(fieldName, fieldValue, store);
			document.add(field);
			it.remove(); // avoids a ConcurrentModificationException
		}
		LOGGER.info("End indexing document : " + indexable);
		return document;
	}

	@Override
	public void indexDocument(Indexable indexable) throws DocumentNotIndexedException {
		Document document;
		try {
			document = getDocument(indexable);
			writer.addDocument(document);
			writer.commit();
			writer.close();
		} catch (DocumentNotIndexedException | IOException e) {
			LOGGER.error("Error while indexing document");
			throw new DocumentNotIndexedException(e);
		}
	}

	private Field createField(String fieldName, Object fieldValue,
			Field.Store store) throws DocumentNotIndexedException {
		Field field;
		if (fieldValue instanceof String) {
			field = new TextField(fieldName, (String) fieldValue, store);
		} else if (fieldValue instanceof File) {
			File file = (File) fieldValue;
			try {
				BodyContentHandler handler = new BodyContentHandler();
				 
			    AutoDetectParser parser = new AutoDetectParser();
			    Metadata metadata = new Metadata();
			    try (FileInputStream stream = new FileInputStream(file);) {
			        parser.parse(stream, handler, metadata);
			        LOGGER.info(handler.toString());
					field = new TextField(fieldName, handler.toString(), store);
			    }
				
			} catch (IOException | SAXException | TikaException e) {
				LOGGER.error("Error while indexing file");
				throw new DocumentNotIndexedException(e.getMessage(), e);
			}
		} else if (fieldValue instanceof Integer) {
			field = new IntField(fieldName, (int) fieldValue, store);
		} else if (fieldValue instanceof Long) {
			field = new LongField(fieldName, (int) fieldValue, store);
		} else if (fieldValue instanceof Float) {
			field = new FloatField(fieldName, (int) fieldValue, store);
		} else if (fieldValue instanceof Double) {
			field = new DoubleField(fieldName, (int) fieldValue, store);
		} else {
			throw new UnsupportedTypeValueException("Unsupported type : "
					+ fieldValue.getClass().getName());
		}
		return field;
	}

}
