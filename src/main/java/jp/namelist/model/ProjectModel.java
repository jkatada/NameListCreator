package jp.namelist.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "project")
public class ProjectModel extends AbstractModel {

	@XmlElement
	private Map<String, PackageModel> packages;
	
	/**
	 *  for JAXB
	 */
	public ProjectModel() {
		this("");
	}
	
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
