package jp.namelist.modelbuilder;

import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import jp.namelist.model.ProjectModel;

public class XMLModelExporter {

	// public void export(Document document) throws IOException,
	// TransformerFactoryConfigurationError, TransformerException {
	// Transformer transformer = TransformerFactory.newInstance()
	// .newTransformer();
	// // 文書形式
	// transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	// // インデントを行う
	// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	// // CDATAでテキストノードを囲むタグの指定
	// transformer
	// .setOutputProperty(OutputKeys.CDATA_SECTION_ELEMENTS, "text");
	//
	// DOMSource source = new DOMSource(document);
	// StreamResult result = new StreamResult(writer);
	// transformer.transform(source, result);
	// }

	public void exportModel(ProjectModel model, Writer writer) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProjectModel.class);
			context.createMarshaller().marshal(model, writer);
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}

	}

}
