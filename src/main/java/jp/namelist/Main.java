package jp.namelist;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

import javax.xml.transform.TransformerFactoryConfigurationError;

import jp.namelist.analyzer.ModelBuilder;
import jp.namelist.analyzer.SourceVisitor;
import jp.namelist.view.HTMLViewExporter;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Main {

	public static void main(String[] args) {
		// String file =
		// "../test.springmvc/src/main/java/test/springmvc/MemberRegisterController.java";
		String file = "../test.springmvc/src/main/java";

		Main main = new Main();
		main.processProject(FileSystems.getDefault().getPath(file));
	}

	private void processProject(Path sourceDir) {

		try {
			Files.walkFileTree(sourceDir, new JavaFileVisitor());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// XMLModelExporter exporter = new XMLModelExporter();
		// exporter.export(collector.getDocument());

		// String xml = exporter.getWriter().toString();
		// System.out.println(xml);
		// System.out.println(collector.getProject());
	}

	public class JavaFileVisitor extends SimpleFileVisitor<Path> {

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {

			String fileName = file.getFileName().toString();
			if (fileName.endsWith(".java") && !fileName.startsWith("package-info")) {
				try (FileInputStream fis = new FileInputStream(file.toFile());
						InputStreamReader isr = new InputStreamReader(fis,
								"UTF-8");
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

					ModelBuilder builder = new ModelBuilder("hogePJ", file
							.getParent().toString(), file.getFileName()
							.toString());
					cu.accept(new SourceVisitor(builder));

					StringWriter output = new StringWriter();
					new HTMLViewExporter(output).export(builder.getProject());
					System.out.println(output.toString());

				} catch (IOException e) {
					e.printStackTrace();
				} catch (TransformerFactoryConfigurationError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				


			}
			return super.visitFile(file, attrs);
		}
	}
}
