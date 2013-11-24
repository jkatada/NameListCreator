package jp.namelist.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;

import jp.namelist.common.CollectionUtil;

public class AnnotationModel extends AbstractModel {
	
	@XmlElement
	private Map<String, AnnotationParameterModel> params;
	
	private String text;
	
	/**
	 *  for JAXB unmarshal
	 */
	public AnnotationModel() {
		this("");
	}
	
	public AnnotationModel(String name) {
		super(name);
		params = new LinkedHashMap<>();
	}
	
	public void addParam(AnnotationParameterModel parameter) {
		params.put(parameter.getName(), parameter);
	}

	public Map<String, AnnotationParameterModel> getParams() {
		return params;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public AnnotationParameterModel getParam(String paramName) {
		return params.get(paramName);
	}
	
	public List<AnnotationParameterModel> getOtherParam(String... excludeParamName) {
		return CollectionUtil.mapToList(params, excludeParamName);
	}

}
