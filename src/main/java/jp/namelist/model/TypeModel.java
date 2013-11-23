package jp.namelist.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class TypeModel extends AbstractModifiableModel {

	private Set<MethodModel> methods;

	// TODO フィールド
	// TODO enum
	
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
	
	
}
