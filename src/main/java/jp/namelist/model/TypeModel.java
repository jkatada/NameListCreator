package jp.namelist.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class TypeModel extends AbstractModifiableModel implements Documentable {

	private String javaDoc;
	
	@XmlElement
	private Set<MethodModel> methods;

	// TODO フィールド
	// TODO enum
	
	/**
	 *  for JAXB unmarshal
	 */
	public TypeModel() {
		this("");
	}
	
	public TypeModel(String name) {
		super(name);
		methods = new LinkedHashSet<>();
	}
	
	public void addMethod(MethodModel method) {
		methods.add(method);
	}

	public Set<MethodModel> getMethods() {
		return methods;
	}

	@Override
	public String getJavaDoc() {
		return javaDoc;
	}

	@Override
	public void setJavaDoc(String javaDoc) {
		this.javaDoc = javaDoc;
	}
	
	
}
