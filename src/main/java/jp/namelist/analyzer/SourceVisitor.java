package jp.namelist.analyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class SourceVisitor extends ASTVisitor {

	private XMLModelBuilder modelBuilder;
	
	private AnalyzeFilter filter = new AnalyzeFilter();

	public SourceVisitor(XMLModelBuilder modelBuilder) {
		this.modelBuilder = modelBuilder;
	}
	
	@Override
	public boolean visit(AnonymousClassDeclaration node) {

		modelBuilder.analyzeNode(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		modelBuilder.upAnalyzeHierarchy();
		super.endVisit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {

		modelBuilder.analyzeNode(node);

		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		modelBuilder.upAnalyzeHierarchy();
		super.endVisit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		modelBuilder.analyzeNode(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(EnumDeclaration node) {
		modelBuilder.upAnalyzeHierarchy();
		super.endVisit(node);
	}
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		modelBuilder.analyzeNode(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(EnumConstantDeclaration node) {
		modelBuilder.upAnalyzeHierarchy();
		super.endVisit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		modelBuilder.analyzeNode(node);
		return super.visit(node);
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
		modelBuilder.upAnalyzeHierarchy();
		super.endVisit(node);
	};
	
	@Override
	public boolean visit(ReturnStatement node) {
		modelBuilder.analyzeNode(node);
		return super.visit(node);
	}

	@Override
	public void endVisit(ReturnStatement node) {
		modelBuilder.upAnalyzeHierarchy();
		super.endVisit(node);
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		if (filter.isTarget(node)) {
			modelBuilder.analyzeNode(node);
		}
		return super.visit(node);
	}
	
	@Override
	public void endVisit(MethodInvocation node) {
		if (filter.isTarget(node)) {
			modelBuilder.upAnalyzeHierarchy();
		}
		super.endVisit(node);
	}

}
