package jp.namelist.modelbuilder.filters;

import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public interface BuildModelFilter {
	public boolean isTarget(PackageDeclaration node);
	public boolean isTarget(AnonymousClassDeclaration node);
	public boolean isTarget(TypeDeclaration node);
	public boolean isTarget(EnumDeclaration node);
	public boolean isTarget(MethodDeclaration node);
	public boolean isTarget(ReturnStatement node);
	public boolean isTarget(MethodInvocation node);
}
