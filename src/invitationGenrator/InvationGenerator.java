package invitationGenrator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class InvationGenerator {
	private static final String XML_FILE_WAY = "./PersonFile.xml";
	private static final String TEMPLATE_WAY = 
			"./docTemplate/template.docx";
	private static final String[] DOC_NAME_PARTS = new String[] {"lastName","name","patronymic"};
	private static final String DOC_TYPE = "Приглашение";
	private static final String DOC_WAY = 
			"./generatedDocuments/";
	private static final Integer DOC_COUNT_GENERATE = 10;
	
	public static void main(String argv[]) {
		ReadXMLFile XMLReader = new ReadXMLFile();
		ReplacePlaceholders replacePlaceholders = new ReplacePlaceholders();
		
		List<Map<String,String>> peopleInfo = XMLReader.getDataFromXML(XML_FILE_WAY);
		if(peopleInfo.size() < DOC_COUNT_GENERATE) {
			System.out.println("Wrong count of new document to created");
			return;
		}
		
		for(int i=0;i < DOC_COUNT_GENERATE;i++) {
			HashMap<String, XWPFDocument> newDoc  = 
					replacePlaceholders.generateNewDoc(peopleInfo.get(i),TEMPLATE_WAY,DOC_NAME_PARTS);
			for(Map.Entry<String, XWPFDocument> entry : newDoc.entrySet()) {
				String newDocWayName = DOC_WAY + DOC_TYPE + "_" + entry.getKey() + ".docx";
				replacePlaceholders.saveNewFile(newDocWayName,newDoc.get(entry.getKey()));
			}
		}
		System.out.println("Documents successfully created");
	}
}
