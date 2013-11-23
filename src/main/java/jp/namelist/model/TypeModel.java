package jp.namelist.model;

import java.util.Set;
import java.util.TreeSet;

public class TypeModel extends AbstractModifiableModel {

	private Set<MethodModel> methodSet;

	// TODO フィールド
	// TODO enum
	
	public TypeModel(String name) {
		super(name);
		methodSet = new TreeSet<>();
	}
	
	public void addMethod(MethodModel method) {
		methodSet.add(method);
	}

	public Set<MethodModel> getMethodSet() {
		return methodSet;
	}
	
	
}
