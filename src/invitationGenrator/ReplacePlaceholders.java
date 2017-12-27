package invitationGenrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ReplacePlaceholders {

	@SuppressWarnings("resource")
	public HashMap<String, XWPFDocument> generateNewDoc(Map<String, String> placeholders, String templateWay,
			String[] docNameParts) {
		HashMap<String, XWPFDocument> docAndDocName = new HashMap<String, XWPFDocument>();
		String docNamePart = generateDocName(placeholders, docNameParts);
		try {
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(templateWay));
			doc = replacePlaceholders(doc, placeholders);
			docAndDocName.put(docNamePart, doc);
		} catch (InvalidFormatException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return docAndDocName;
	}

	private String generateDocName(Map<String, String> placeholders, String[] docNameParts) {
		String docName = "";
		for (String namePart : docNameParts) {
			docName += placeholders.get(namePart);
		}
		return docName;
	}

	private XWPFDocument replacePlaceholders(XWPFDocument doc, Map<String, String> placeholders) {
		for (XWPFParagraph p : doc.getParagraphs()) {
			List<XWPFRun> runs = p.getRuns();
			if (runs == null) {
				continue;
			}
			for (XWPFRun r : runs) {
				String text = r.getText(0);
				for (Map.Entry<String, String> entry : placeholders.entrySet()) {
					if (text != null && text.contains("$" + entry.getKey())) {
						text = text.replace("$" + entry.getKey(), entry.getValue());
						r.setText(text, 0);
					}
				}
			}
		}
		return doc;
	}

	public Boolean saveNewFile(String newDocWayName, XWPFDocument newDoc) {
		try {
			FileOutputStream out = new FileOutputStream(new File(newDocWayName + ".docx"));
			newDoc.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
//			e.printStackTrace();
		}
		return true;
	}

}
