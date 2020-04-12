package fernuni.propra.file_processing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XmlReader {
	
	private String xmlPath;
//	private Room room;
	
	public XmlReader(String xmlPath) throws JDOMException, IOException {
		
		this.xmlPath = xmlPath;
		
		this.createRoom();
	}
	
	private void createRoom() throws JDOMException, IOException {
		
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(xmlPath);
		Document jdomDoc = (Document) builder.build(xmlFile);
		Element root = jdomDoc.getRootElement();
		
		String id = getId(root);
		List<Corner> corners = getCorners(root);
		
		
		System.out.println(corners);
		
		System.out.println(id);
	}
	
	private String getId(Element root) {
		return root.getChildText("ID");
	}
	
	private List<Corner> getCorners(Element root) {
		List<Corner> corners = new ArrayList<>();
		List<Element> cornerElements = root.getChild("ecken").getChildren();

		cornerElements.stream().forEach(cornerElement -> {
			float x = Float.parseFloat(cornerElement.getChildText("x"));
			float y = Float.parseFloat(cornerElement.getChildText("y"));
			corners.add(new Corner(x, y));
		});
		
		return corners;
	}
	
	private List<Lamp> getLamps(Element root) {
		List<Lamp> lamps = new ArrayList<>();
		List<Element> cornerElements = root.getChild("ecken").getChildren();

		cornerElements.stream().forEach(cornerElement -> {
			float x = Float.parseFloat(cornerElement.getChildText("x"));
			float y = Float.parseFloat(cornerElement.getChildText("y"));
			lamps.add(new Lamp(x, y));
		});
		
		return lamps;
	}
	
}
