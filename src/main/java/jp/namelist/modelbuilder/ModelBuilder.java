package jp.namelist.modelbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;

import javax.xml.transform.TransformerFactoryConfigurationError;

import jp.namelist.model.ProjectModel;
import jp.namelist.modelbuilder.filters.BuildModelFilter;
import jp.namelist.modelbuilder.filters.DefaultBuildModelFilter;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ModelBuilder {

	public ProjectModel build(String projectName, Path sourceDir) throws IOException {
		return build(projectName, sourceDir, new DefaultBuildModelFilter());
	}

	public ProjectModel build(String projectName, Path sourceDir,
			BuildModelFilter filter) throws IOException {

		BuildModelVisitor builder = new BuildModelVisitor(projectName, filter);
		Files.walkFileTree(sourceDir, new JavaFileVisitor(builder));
		
		return builder.getProject();
	}

	private class JavaFileVisitor extends SimpleFileVisitor<Path> {

		private BuildModelVisitor builder;

		public JavaFileVisitor(BuildModelVisitor builder) {
			this.builder = builder;
		}

		@Override
		public FileVisitResult visitFile(Path sourcePath,
				BasicFileAttributes attrs) throws IOException {

			String fileName = sourcePath.getFileName().toString();
			if (!fileName.endsWith(".java")
					|| fileName.equals("package-info.java")) {
				return super.visitFile(sourcePath, attrs);
			}

			CompilationUnit cu = parserJavaSource(readJavaSource(sourcePath));

			cu.accept(builder);

			return super.visitFile(sourcePath, attrs);
		}

		private String readJavaSource(Path sourcePath) {
			StringWriter sw = new StringWriter();
			try (BufferedReader br = Files.newBufferedReader(sourcePath,
					Charset.forName("UTF-8"))) {
				int read;
				while ((read = br.read()) != -1) {
					sw.write(read);
				}
			} catch (IOException | TransformerFactoryConfigurationError e) {
				throw new IllegalStateException(e);
			}
			return sw.toString();
		}

		private CompilationUnit parserJavaSource(String source) {
			ASTParser parser = ASTParser.newParser(AST.JLS4);
			parser.setSource(source.toCharArray());

			// コンパイラバージョンの指定。enumを認識するために必要。
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_7, options);
			parser.setCompilerOptions(options);

			return (CompilationUnit) parser
					.createAST(new NullProgressMonitor());
		}
	}

}
