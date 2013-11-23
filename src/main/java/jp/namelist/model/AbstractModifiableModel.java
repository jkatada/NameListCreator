package jp.namelist.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import jp.namelist.common.CollectionUtil;

public abstract class AbstractModifiableModel extends AbstractModel {

	private Map<String, AnnotationModel> annotations;
	private Set<String> modifiers;
	
	public AbstractModifiableModel(String name) {
		super(name);
		annotations = new TreeMap<>();
		modifiers = new TreeSet<>();
	}
	
	public void addAnnotation(AnnotationModel annotation) {
		annotations.put(annotation.getName(), annotation);
	}
	
	public void addModifier(String modifier) {
		modifiers.add(modifier);
	}

	public Map<String, AnnotationModel> getAnnotations() {
		return annotations;
	}

	public Set<String> getModifiers() {
		return modifiers;
	}
	
	public AnnotationModel getAnnotation(String annotationName) {
		return annotations.get(annotationName);
	}
	
	/**
	 * 引数に指定されたアノテーションを除いたアノテーション定義の文字列表現を返します。
	 * 
	 * @param annotationNames
	 * @return
	 */
	public List<AnnotationModel> getOtherAnnotations(String... excludeAnnotationNames) {
		return CollectionUtil.mapToList(annotations, excludeAnnotationNames);
	}
	
}
