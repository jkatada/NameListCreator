package jp.namelist.model;

import java.util.Set;
import java.util.TreeSet;


public class ProjectModel extends AbstractModel {

	private Set<PackageModel> packageSet;
	
	public ProjectModel(String name) {
		super(name);
		packageSet = new TreeSet<>();
	}
	
	public void addPackage(PackageModel packageModel) {
		packageSet.add(packageModel);
	}

	public Set<PackageModel> getPackageSet() {
		return packageSet;
	}
	
	
}
