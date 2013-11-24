package jp.namelist;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.lang3.StringUtils;

import jp.namelist.model.ProjectModel;
import jp.namelist.modelbuilder.ModelBuilder;
import jp.namelist.view.VelocityViewExporter;

public class Main {

	public static void main(String[] args) {
		
		String projectName = "";
		String sourceDir = "../test.springmvc/src/main/java"; 
		String htmlOutput = "";
		// String htmlOutput = "z:/output.html";
		
		Path sourceDirPath = FileSystems.getDefault().getPath(sourceDir);
		ModelBuilder builder = new ModelBuilder();

		try {
			ProjectModel model = builder.build(projectName, sourceDirPath);

			if (StringUtils.isEmpty(htmlOutput)) {
				new VelocityViewExporter().export(model, new PrintWriter(System.out));
			} else {
				new VelocityViewExporter().export(model, htmlOutput);
			}
			
			// System.out.println(builder.getProject());

			// new XMLModelExporter().exportModel(builder.getProject(), new
			// PrintWriter(System.out));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
