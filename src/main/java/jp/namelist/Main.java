package jp.namelist;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import jp.namelist.model.ProjectModel;
import jp.namelist.modelbuilder.ModelBuilder;
import jp.namelist.modelbuilder.XMLModelExporter;
import jp.namelist.modelbuilder.filters.BuildModelFilter;
import jp.namelist.modelbuilder.filters.ControllerBuildModelFilter;
import jp.namelist.modelbuilder.filters.DefaultBuildModelFilter;
import jp.namelist.view.VelocityViewExporter;

import org.apache.commons.lang3.StringUtils;

public class Main {

	public static void main(String[] args) {

		if (args.length < 3) {
			System.out
					.println("usage: jp.namelist.Main projectname sourceDir type [output]");
			System.out
					.println("    type: all, controller, service, repository");
		}

		String projectName = args[0];
		String sourceDir = args[1];
		String type = args[2];
		String htmlOutput = "";
		if (args.length >= 4) {
			htmlOutput = args[3];
		}

		System.out.println(projectName);
		System.out.println(sourceDir);
		System.out.println(type);
		System.out.println(htmlOutput);

		Path sourceDirPath = FileSystems.getDefault().getPath(sourceDir);
		ModelBuilder builder = new ModelBuilder();

		try {
			// TODO typeのスイッチではなく、自由に設定ファイルを渡せるようにする。
			String template = "";
			BuildModelFilter filter = new DefaultBuildModelFilter();
			switch (type) {
			case "all":
				template = "template/view/htmlTemplate.html";
				break;
			case "controller":
				template = "template/view/htmlTemplate_controller.html";
				filter = new ControllerBuildModelFilter();
				break;
			case "service":
				template = "template/view/htmlTemplate_service.html";
				break;
			case "repository":
				template = "template/view/htmlTemplate_repository.html";
				break;
			default:
				throw new IllegalArgumentException(
						"type argument is illegal. type: " + type);
			}

			ProjectModel model = builder.build(projectName, sourceDirPath, filter);

			if (StringUtils.isEmpty(htmlOutput)) {
				new VelocityViewExporter().export(model, new PrintWriter(
						System.out), template, "UTF-8");
			} else {
				new VelocityViewExporter().export(model, htmlOutput, template,
						"UTF-8");
			}

			// System.out.println(builder.getProject());

			new XMLModelExporter().exportModel(model, new PrintWriter(
					System.out));

		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

}
