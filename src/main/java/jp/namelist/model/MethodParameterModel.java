package jp.namelist.model;


public class MethodParameterModel extends AbstractModifiableModel {

	private String type;
	
	private String text;
	
	public MethodParameterModel(String name) {
		super(name);
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
