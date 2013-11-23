package jp.namelist.model;

import java.util.LinkedHashMap;
import java.util.Map;


public class ProjectModel extends AbstractModel {

	private Map<String, PackageModel> packages;
	
	public ProjectModel(String name) {
		super(name);
		packages = new LinkedHashMap<>();
	}
	
	public void addPackage(PackageModel packageModel) {
		packages.put(packageModel.getName(), packageModel);
	}

	public Map<String, PackageModel> getPackages() {
		return packages;
	}
	
	
}
