package jp.namelist.model;

import java.util.Map;
import java.util.TreeMap;

public class AnnotationModel extends AbstractModel {
	
	private Map<String, String> paramMap;
	private String text;
	
	public AnnotationModel(String name) {
		super(name);
		paramMap = new TreeMap<>();
	}
	
	public void addParam(String paramName, String paramValue) {
		paramMap.put(paramName, paramValue);
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
