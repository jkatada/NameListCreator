package jp.namelist.model;

import java.util.Set;
import java.util.TreeSet;

public class PackageModel extends AbstractModel {

	private Set<TypeModel> types;

	public PackageModel(String name) {
		super(name);
		types = new TreeSet<>();
	}
	
	public Set<TypeModel> getTypes() {
		return types;
	}

	public void addType(TypeModel typeModel) {
		types.add(typeModel);
	}
	
}
