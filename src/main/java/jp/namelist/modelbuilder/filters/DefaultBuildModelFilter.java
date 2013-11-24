package jp.namelist.modelbuilder.filters;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DefaultBuildModelFilter implements BuildModelFilter {

	public boolean isTarget(PackageDeclaration node) {
		return true;
	}
	
	public boolean isTarget(AnonymousClassDeclaration node) {
		return true;
	}

	public boolean isTarget(TypeDeclaration node) {
		return true;
	}

	public boolean isTarget(EnumDeclaration node) {
		return true;
	}

	public boolean isTarget(MethodDeclaration node) {
		return true;
	}

	public boolean isTarget(ReturnStatement node) {
		return true;
	}

	public boolean isTarget(MethodInvocation node) {
		// メソッド呼び出しの情報は膨大なので、デフォルトでは取らない。
		return false;
	}
}
