package jp.namelist.model;


public class AnnotationParameterModel extends AbstractModel {

	private String value;
	
	public AnnotationParameterModel(String name) {
		super(name);
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return getName() + " = " + value;
	}

}
