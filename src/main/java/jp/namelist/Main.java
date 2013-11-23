package jp.namelist;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import jp.namelist.analyzer.SourceVisitor;
import jp.namelist.analyzer.XMLModelExporter;
import jp.namelist.analyzer.XMLModelBuilder;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Main {

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

			CompilationUnit cu = (CompilationUnit) parser
					.createAST(new NullProgressMonitor());

			XMLModelBuilder collector = new XMLModelBuilder();
			cu.accept(new SourceVisitor(collector));

			XMLModelExporter exporter = new XMLModelExporter();
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
