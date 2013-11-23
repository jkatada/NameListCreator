package jp.namelist.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.namelist.model.AnnotationModel;
import jp.namelist.model.CompilationUnitModel;
import jp.namelist.model.MethodModel;
import jp.namelist.model.MethodParameterModel;
import jp.namelist.model.PackageModel;
import jp.namelist.model.ProjectModel;
import jp.namelist.model.TypeModel;

public class HTMLViewExporter {

	private Writer writer;

	public HTMLViewExporter(Writer writer) {
		this.writer = writer;
	}

	public void export(ProjectModel model) {

		prepare();

		try {
			process(model);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void process(ProjectModel model) throws IOException {
		for (PackageModel packageModel : model.getPackageSet()) {
			process(packageModel, createTD(model.getName()));
		}
	}

	private void process(PackageModel packageModel, String line)
			throws IOException {
		for (CompilationUnitModel cu : packageModel.getCompilationUnitSet()) {
			process(cu, line + createTD(packageModel.getName()));
		}
	}

	private void process(CompilationUnitModel cu, String line)
			throws IOException {
		for (TypeModel type : cu.getTypeSet()) {
			process(type, line + createTD(cu.getName()));
		}
	}

	private void process(TypeModel type, String line) throws IOException {
		String typeLine = line;
		String otherAnnotation = "";
		boolean hasRequestMapping = false;
		for (AnnotationModel annotation : type.getAnnotationSet()) {
			if ("RequestMapping".equals(annotation.getName())) {
				Map<String, String> paramMap = annotation.getParamMap();
				typeLine += createTD(paramMap.get("method"));
				typeLine += createTD(paramMap.get("value"));
				typeLine += createTD(paramMap.get("params"));
				hasRequestMapping = true;
			} else {
				otherAnnotation += annotation.getText() + "<br>";
			}
		}
		if (!hasRequestMapping) {
			typeLine += createTD("");
			typeLine += createTD("");
			typeLine += createTD("");
		}
		typeLine += createTD(otherAnnotation);

		for (MethodModel method : type.getMethodSet()) {
			process(method, typeLine + createTD(type.getName()));
		}

	}

	private void process(MethodModel method, String line) throws IOException {
		String methodLine = line;
		methodLine += processMethodAnnotation(method.getAnnotationSet());
		methodLine += createTD(method.getName());
		methodLine += processMethodParameter(method.getParamSet());
		writeLine(methodLine);
	}

	private String processMethodAnnotation(Set<AnnotationModel> annotationSet) {
		String line = "";
		boolean hasRequestMapping = false;
		String otherAnnotation = "";
		for (AnnotationModel annotation : annotationSet) {
			if ("RequestMapping".equals(annotation.getName())) {
				hasRequestMapping = true;
				TreeMap<String, String> tmpMap = new TreeMap<>(
						annotation.getParamMap());
				line += createTD(tmpMap.remove("method"));
				line += createTD(tmpMap.remove("value"));
				line += createTD(tmpMap.remove("params"));
				
				String otherParam = "";
				for (String key : tmpMap.keySet()) {
					otherParam += key + "=" + tmpMap.get(key)
							+ "<br>";
				}
				line += createTD(otherParam);
			} else {
				otherAnnotation += annotation.getText() + "<br>";
			}
		}
		if (!hasRequestMapping) {
			line += createTD("");
			line += createTD("");
			line += createTD("");
			line += createTD("");
		}
		line += createTD(otherAnnotation);
		return line;
	}

	private String processMethodParameter(Set<MethodParameterModel> paramSet) {
		String line = "";
		for(MethodParameterModel param : paramSet) {
			line += param.getText() + "<br>";
		}
		return createTD(line);
	}


	private void writeLine(String line) throws IOException {
		writer.append("<tr>" + line + "</tr>\n");
	}

	private String createTD(String contents) {
		return "<td>" + ((contents == null) ? "" : contents) + "</td>";
	}

	private void prepare() {
		// writer.append("")
	}

	public Writer getWriter() {
		return writer;
	}

}
