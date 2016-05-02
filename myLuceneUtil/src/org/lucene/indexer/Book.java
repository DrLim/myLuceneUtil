package org.lucene.indexer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.lucene.utils.FieldType;

public class Book implements Indexable{
	
	private Integer id ;
	private String title;
	private String path;


	public Book(Integer id, String title, String path) {
		this.id = id ;
		this.title = title ;
		this.path = path ;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public Map<String, Object> getFieldsValue() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("title", title);
		map.put("content", new File(path));
		return map;
		
	}

	@Override
	public Map<String, Boolean> getStoredFields() {
		Map<String, Boolean> map = new HashMap<>();
		map.put("id", Boolean.TRUE);
		map.put("title", Boolean.TRUE);
		map.put("content", Boolean.TRUE);
		return map;
	}

	@Override
	public Map<String, FieldType> getSearchField() {
		Map<String, FieldType> map = new HashMap<>();
		map.put("title", FieldType.STRING);
		map.put("content", FieldType.FILE);
		return map;
	}


}
