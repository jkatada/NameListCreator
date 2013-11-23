package jp.namelist.view;

import java.io.StringWriter;

import jp.namelist.model.ProjectModel;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class VelocityViewExporter {

	private static final String HTML_TEMPLATE = "template/view/htmlTemplate.html";
	private static final String HTML_TEMPLATE_CHARSET = "UTF-8";

	public void export(ProjectModel project) {
		
		StringWriter output = new StringWriter();
		
		Velocity.init();
		VelocityContext context = new VelocityContext();
		context.put("project", project);
		Template template = Velocity.getTemplate(HTML_TEMPLATE, HTML_TEMPLATE_CHARSET);
		template.merge(context, output);

		System.out.println(output.toString());

	}
	
}
