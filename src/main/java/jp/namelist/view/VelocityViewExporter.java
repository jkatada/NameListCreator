package jp.namelist.view;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import jp.namelist.model.ProjectModel;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityViewExporter {

	private static final String HTML_TEMPLATE = "template/view/htmlTemplate.html";
	private static final String HTML_TEMPLATE_CHARSET = "UTF-8";
	private static final String DEFAULT_OUTPUT_CHARSET = "UTF-8";

	public void export(ProjectModel project, String outputFilePath)
			throws IOException {
		Writer writer = Files.newBufferedWriter(FileSystems.getDefault()
				.getPath(outputFilePath), Charset.forName(DEFAULT_OUTPUT_CHARSET));
		export(project, writer, HTML_TEMPLATE, HTML_TEMPLATE_CHARSET);
	}

	public void export(ProjectModel project, Writer writer) throws IOException {
		export(project, writer, HTML_TEMPLATE, HTML_TEMPLATE_CHARSET);
	}

	public void export(ProjectModel project, Writer writer,
			String templatePath, String templateCharset) throws IOException {

		Velocity.init();
		VelocityContext context = new VelocityContext();
		context.put("project", project);
		Template template = Velocity.getTemplate(templatePath, templateCharset);
		template.merge(context, writer);
		
		writer.flush();

	}

}
