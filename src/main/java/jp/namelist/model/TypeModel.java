package jp.namelist.model;

import java.util.Set;
import java.util.TreeSet;

public class TypeModel extends AbstractModifiableModel {

	private Set<MethodModel> methods;

	// TODO フィールド
	// TODO enum
	
	public TypeModel(String name) {
		super(name);
		methods = new TreeSet<>();
	}
	
	public void addMethod(MethodModel method) {
		methods.add(method);
	}

	public Set<MethodModel> getMethods() {
		return methods;
	}
	
	
}
