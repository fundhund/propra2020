package fernuni.propra.file_processing;

import java.awt.geom.Point2D;
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
	
	public XmlReader(String xmlPath) throws JDOMException, IOException {
		this.xmlPath = xmlPath;
	}
	
	public Room createRoom() throws JDOMException, IOException {
		
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File(xmlPath);
		Document jdomDoc = (Document) builder.build(xmlFile);
		Element root = jdomDoc.getRootElement();
		
		String id = getId(root);
		List<Point2D.Float> corners = getPoints(root, "ecken");
		List<Point2D.Float> lamps = getPoints(root, "lampen");
		
		return new Room(id, corners, lamps);
	}
	
	private String getId(Element root) {
		return root.getChildText("ID");
	}
	
	private List<Point2D.Float> getPoints(Element root, String type) {
		List<Point2D.Float> points = new ArrayList<>();
		
		Element parent = root.getChild(type);
		if (parent != null) {
			List<Element> pointElements = parent.getChildren();
			
			pointElements.stream().forEach(pointElement -> {
				float x = Float.parseFloat(pointElement.getChildText("x"));
				float y = Float.parseFloat(pointElement.getChildText("y"));
				points.add(new Point2D.Float(x, y));
			});
		}
		
		return points;
	}
}
