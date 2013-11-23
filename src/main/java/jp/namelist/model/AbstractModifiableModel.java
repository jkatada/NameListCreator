package jp.namelist.model;

import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractModifiableModel extends AbstractModel {

	private Set<AnnotationModel> annotationSet;
	private Set<String> modifierSet;
	
	public AbstractModifiableModel(String name) {
		super(name);
		annotationSet = new TreeSet<>();
		modifierSet = new TreeSet<>();
	}
	
	public void addAnnotation(AnnotationModel annotation) {
		annotationSet.add(annotation);
	}
	
	public void addModifier(String modifier) {
		modifierSet.add(modifier);
	}

	public Set<AnnotationModel> getAnnotationSet() {
		return annotationSet;
	}

	public Set<String> getModifierSet() {
		return modifierSet;
	}
	
	
}
