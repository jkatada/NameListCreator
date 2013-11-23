package jp.namelist.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class PackageModel extends AbstractModel {

	private Set<TypeModel> types;

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
	
}
