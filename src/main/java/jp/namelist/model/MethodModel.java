package jp.namelist.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class MethodModel extends AbstractModifiableModel {

	private String returnType;
	private Set<MethodParameterModel> params;
	private Set<String> methodInvocations;
	private Set<String> methodReturns;

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
