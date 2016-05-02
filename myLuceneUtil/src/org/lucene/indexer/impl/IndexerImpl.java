package org.lucene.indexer.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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
import org.lucene.indexer.Indexer;
import org.lucene.utils.FieldDefinition;
import org.lucene.utils.FieldType;
import org.lucene.utils.Indexable;
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
		List<FieldDefinition> fields = indexable.getFields();
		for(FieldDefinition fieldDef : fields){
			Field field = createField(fieldDef);
			document.add(field);
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

	private Field createField(FieldDefinition fieldDef) throws DocumentNotIndexedException {
		Field field;
		if (FieldType.STRING.equals(fieldDef.getType())) {
			field = new TextField(fieldDef.getName(), (String) fieldDef.getValue(), fieldDef.getSotre());
		} 
		else if (FieldType.FILE.equals(fieldDef.getType())) {
			File file = (File) fieldDef.getValue();
			try {
				BodyContentHandler handler = new BodyContentHandler();
			    AutoDetectParser parser = new AutoDetectParser();
			    Metadata metadata = new Metadata();
			    try (FileInputStream stream = new FileInputStream(file);) {
			        parser.parse(stream, handler, metadata);
			        LOGGER.info(handler.toString());
					field = new TextField(fieldDef.getName(), handler.toString(), fieldDef.getSotre());
			    }
				
			} catch (IOException | SAXException | TikaException e) {
				LOGGER.error("Error while indexing file");
				throw new DocumentNotIndexedException(e.getMessage(), e);
			}
		} else if (FieldType.INTEGER.equals(fieldDef.getType())) {
			field = new IntField(fieldDef.getName(), (int) fieldDef.getValue(), fieldDef.getSotre());
		} else if (FieldType.LONG.equals(fieldDef.getType())) {
			field = new LongField(fieldDef.getName(), (long) fieldDef.getValue(), fieldDef.getSotre());
		} else if (FieldType.FLOAT.equals(fieldDef.getType())) {
			field = new FloatField(fieldDef.getName(), (float) fieldDef.getValue(), fieldDef.getSotre());
		} else if (FieldType.DOUBLE.equals(fieldDef.getType())) {
			field = new DoubleField(fieldDef.getName(), (double) fieldDef.getValue(), fieldDef.getSotre());
		} else {
			throw new UnsupportedTypeValueException("Unsupported type : "
					+ fieldDef.getValue().getClass().getName());
		}
		return field;
	}

}
