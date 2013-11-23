package jp.namelist.analyzer;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class SourceVisitor extends ASTVisitor {

	private ModelBuilder modelBuilder;
	
	private AnalyzeFilter filter = new AnalyzeFilter();

	public SourceVisitor(ModelBuilder modelBuilder) {
		this.modelBuilder = modelBuilder;
	}
	
	@Override
	public boolean visit(AnonymousClassDeclaration node) {

		modelBuilder.analyzeNode(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		modelBuilder.analyzeNodeEnd(node);
		super.endVisit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {

		modelBuilder.analyzeNode(node);

		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		modelBuilder.analyzeNodeEnd(node);
		super.endVisit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		modelBuilder.analyzeNode(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(EnumDeclaration node) {
		modelBuilder.analyzeNodeEnd(node);
		super.endVisit(node);
	}
	@Override
	public boolean visit(MethodDeclaration node) {
		modelBuilder.analyzeNode(node);
		return super.visit(node);
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
		modelBuilder.analyzeNodeEnd(node);
		super.endVisit(node);
	};
	
	@Override
	public boolean visit(ReturnStatement node) {
		modelBuilder.analyzeNode(node);
		return super.visit(node);
	}

	@Override
	public boolean visit(MethodInvocation node) {
		if (filter.isTarget(node)) {
			modelBuilder.analyzeNode(node);
		}
		return super.visit(node);
	}
}
