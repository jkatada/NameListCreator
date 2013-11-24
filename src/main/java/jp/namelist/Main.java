package jp.namelist;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import jp.namelist.model.ProjectModel;
import jp.namelist.modelbuilder.ModelBuilder;
import jp.namelist.modelbuilder.XMLModelExporter;
import jp.namelist.view.VelocityViewExporter;

import org.apache.commons.lang3.StringUtils;

public class Main {

	public static void main(String[] args) {
		
		if (args.length <= 1) {
			System.out.println("usage: jp.namelist.Main projectname sourceDir [output]");
		}
		
		String projectName = args[0];
		String sourceDir = args[1];
		String htmlOutput = "";
		if (args.length >= 3) {
		    htmlOutput = args[2];
		}
		
		System.out.println(projectName);
		System.out.println(sourceDir);
		System.out.println(htmlOutput);
		
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

			new XMLModelExporter().exportModel(model, new
			 PrintWriter(System.out));

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}
