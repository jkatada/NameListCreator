package jp.namelist.model;

import java.util.Set;
import java.util.TreeSet;

public class PackageModel extends AbstractModel {

	private Set<CompilationUnitModel> compilationUnitSet;

	public PackageModel(String name) {
		super(name);
		compilationUnitSet = new TreeSet<>();
	}
	
	public Set<CompilationUnitModel> getCompilationUnitSet() {
		return compilationUnitSet;
	}

	public void addCompilationUnit(CompilationUnitModel compilationUnit) {
		compilationUnitSet.add(compilationUnit);
	}
	
	
}
