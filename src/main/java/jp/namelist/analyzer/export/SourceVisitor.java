package jp.namelist.analyzer.export;

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

	private XMLCollector collector;

	public SourceVisitor(XMLCollector collector) {
		this.collector = collector;
	}
	
	@Override
	public boolean visit(AnonymousClassDeclaration node) {

		collector.collectStart(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		collector.collectEnd();
		super.endVisit(node);
	}

	@Override
	public boolean visit(TypeDeclaration node) {

		collector.collectStart(node);

		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		collector.collectEnd();
		super.endVisit(node);
	}

	@Override
	public boolean visit(EnumDeclaration node) {
		collector.collectStart(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(EnumDeclaration node) {
		collector.collectEnd();
		super.endVisit(node);
	}
	@Override
	public boolean visit(EnumConstantDeclaration node) {
		collector.collectStart(node);

		return super.visit(node);
	}
	@Override
	public void endVisit(EnumConstantDeclaration node) {
		collector.collectEnd();
		super.endVisit(node);
	}
	
	@Override
	public boolean visit(MethodDeclaration node) {
		collector.collectStart(node);
		return super.visit(node);
	}
	
	@Override
	public void endVisit(MethodDeclaration node) {
		collector.collectEnd();
		super.endVisit(node);
	};
	
	@Override
	public boolean visit(ReturnStatement node) {
		collector.collectStart(node);
		return super.visit(node);
	}

	@Override
	public void endVisit(ReturnStatement node) {
		collector.collectEnd();
		super.endVisit(node);
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		// TODO 絞り込みは別のクラスに委譲する
		// model.addAttributeの呼び出しを絞込
		if ("addAttribute".equals(node.getName().getIdentifier())) {
			collector.collectStart(node);
		}
		return super.visit(node);
	}
	
	@Override
	public void endVisit(MethodInvocation node) {
		if ("addAttribute".equals(node.getName().getIdentifier())) {
			collector.collectEnd();
		}
		super.endVisit(node);
	}

	public static void main(String[] args) {
		String file = "../test.springmvc/src/main/java/test/springmvc/MemberRegisterController.java";
		
		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				StringWriter sw = new StringWriter()) {
			int read;
			while ((read = br.read()) != -1) {
				sw.write(read);
			}

			ASTParser parser = ASTParser.newParser(AST.JLS4);
			parser.setSource(sw.toString().toCharArray());
			
			// コンパイラバージョンの指定。enumを認識するために必要。
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
			parser.setCompilerOptions(options);
			
			CompilationUnit cu = (CompilationUnit) parser.createAST(new NullProgressMonitor());

			XMLCollector collector = new XMLCollector();
			cu.accept(new SourceVisitor(collector));
			
			XMLExporter exporter = new XMLExporter();
			exporter.export(collector.getDocument());

			String xml = exporter.getWriter().toString();
			System.out.println(xml);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

	}
	

	
}
