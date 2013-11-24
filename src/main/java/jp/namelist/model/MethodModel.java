package jp.namelist.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

public class MethodModel extends AbstractModifiableModel {

	private String returnType;
	@XmlElement
	private Set<MethodParameterModel> params;
	@XmlElement
	private Set<String> methodInvocations;
	@XmlElement
	private Set<String> methodReturns;
	
	/**
	 *  for JAXB unmarshal
	 */
	public MethodModel() {
		this("");
	}
	
	public MethodModel(String name) {
		super(name);
		params = new LinkedHashSet<>();
		methodInvocations = new LinkedHashSet<>();
		methodReturns = new LinkedHashSet<>();
	}
	
	public void addParam(MethodParameterModel param) {
		params.add(param);
	}
	
	public void addMethodInvocation(String methodInvocation) {
		methodInvocations.add(methodInvocation);
	}
	
	public void addMethodReturn(String methodReturn) {
		methodReturns.add(methodReturn);
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Set<MethodParameterModel> getParams() {
		return params;
	}

	public Set<String> getMethodInvocations() {
		return methodInvocations;
	}

	public Set<String> getMethodReturns() {
		return methodReturns;
	}

}
