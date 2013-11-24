package jp.namelist.modelbuilder;

import java.io.Reader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import jp.namelist.model.ProjectModel;

public class XMLModelImporter {

	public ProjectModel importModel(Reader reader) {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(ProjectModel.class);
			return (ProjectModel) context.createUnmarshaller().unmarshal(reader);
		} catch (JAXBException e) {
			throw new IllegalStateException(e);
		}
	}
}
