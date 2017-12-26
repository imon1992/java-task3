package invitationGenrator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadXMLFile {

	public List<Map<String, String>> getDataFromXML(String fileWay) {
		List<Map<String, String>> peopleInfoListMap = new ArrayList<Map<String, String>>();
		try {
			File fXmlFile = new File(fileWay);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document xmlDoc = dBuilder.parse(fXmlFile);
			xmlDoc.getDocumentElement().normalize();
			Node rootElement = xmlDoc.getDocumentElement();
			NodeList peopleXml = rootElement.getChildNodes();
			int j = 0;
			for (int i = 0; i < peopleXml.getLength(); i++) {
				NodeList people = peopleXml.item(i).getChildNodes();
				if (peopleXml.item(i).getNodeType() != Node.TEXT_NODE) {
					peopleInfoListMap.add(new HashMap<String, String>());
					for (int k = 0; k < people.getLength(); k++) {
						Node humanInfo = people.item(k);
						if (humanInfo.getNodeType() != Node.TEXT_NODE) {
							peopleInfoListMap.get(j).put(humanInfo.getNodeName(), 
									humanInfo.getTextContent());
						}
					}
					j++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return peopleInfoListMap;
	}
}
