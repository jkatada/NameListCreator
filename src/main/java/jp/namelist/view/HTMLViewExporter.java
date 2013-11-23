package jp.namelist.view;

import java.io.IOException;
import java.io.Writer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HTMLViewExporter {

	private Writer writer;

	public HTMLViewExporter(Writer writer) {
		this.writer = writer;
	}

	public void export(Document model) {

		prepare();

		try {

			NodeList children = model.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Element child = (Element) children.item(i);

				if ("type".equals(child.getNodeName())) {
					writer.append("<td>" + child.getAttribute("name") + "</td>");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void prepare() {
		//writer.append("")
	}

	public Writer getWriter() {
		return writer;
	}

}
