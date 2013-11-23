package jp.namelist.analyzer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class XMLModelExporter {

	private Writer writer;

	public XMLModelExporter() {
		this(new StringWriter());
	}

	public XMLModelExporter(Writer writer) {
		this.writer = writer;
	}

	public void export(Document document) throws IOException,
			TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		// 文書形式
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		// インデントを行う
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// CDATAでテキストノードを囲むタグの指定
		transformer.setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS,
				"text");

		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(writer);
		transformer.transform(source, result);
	}

	public Writer getWriter() {
		return writer;
	}
}
