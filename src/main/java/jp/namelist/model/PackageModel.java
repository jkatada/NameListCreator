package jp.namelist.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class PackageModel extends AbstractModel implements Documentable {

	private String javaDoc;
	
	@XmlElement
	private Set<TypeModel> types;
	
	/**
	 *  for JAXB unmarshal
	 */
	public PackageModel() {
		this("");
	}
	
	public PackageModel(String name) {
		super(name);
		types = new LinkedHashSet<>();
	}
	
	public Set<TypeModel> getTypes() {
		return types;
	}

	public void addType(TypeModel typeModel) {
		types.add(typeModel);
	}

	@Override
	public void setJavaDoc(String javaDoc) {
		this.javaDoc = javaDoc;
	}

	@Override
	public String getJavaDoc() {
		return javaDoc;
	}
	
}
