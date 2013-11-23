package jp.namelist.model;

import java.util.Set;
import java.util.TreeSet;

public class CompilationUnitModel extends AbstractModel {

	private Set<TypeModel> typeSet;
	
	public CompilationUnitModel(String name) {
		super(name);
		typeSet = new TreeSet<>();
	}
	
	public void addType(TypeModel type) {
		typeSet.add(type);
	}

	public Set<TypeModel> getTypeSet() {
		return typeSet;
	}
	
}
