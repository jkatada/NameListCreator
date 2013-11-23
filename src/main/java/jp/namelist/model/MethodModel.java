package jp.namelist.model;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class MethodModel extends AbstractModifiableModel {

	private String returnType;
	private Set<MethodParameterModel> paramSet;
	private Set<String> methodInvocationSet;
	private Set<String> methodReturnSet;

	public MethodModel(String name) {
		super(name);
		paramSet = new TreeSet<>();
		methodInvocationSet = new TreeSet<>();
		methodReturnSet = new TreeSet<>();
	}
	
	public void addParam(MethodParameterModel param) {
		paramSet.add(param);
	}
	
	public void addMethodInvocation(String methodInvocation) {
		methodInvocationSet.add(methodInvocation);
	}
	
	public void addMethodReturn(String methodReturn) {
		methodReturnSet.add(methodReturn);
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public Set<MethodParameterModel> getParamSet() {
		return paramSet;
	}

	public Set<String> getMethodInvocationSet() {
		return methodInvocationSet;
	}

	public Set<String> getMethodReturnSet() {
		return methodReturnSet;
	}


}
