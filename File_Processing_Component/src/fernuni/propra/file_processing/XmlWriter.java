package fernuni.propra.file_processing;

import java.awt.geom.Point2D;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlWriter {
	
	private Room room;
	
	public XmlWriter(Room room) {
		this.room = room;
	}
	
	public void writeXml(String fileName) throws IOException {
		
      Document document = new Document();
      
      Element raum = new Element("raum");

      Element id = new Element("ID");
      id.setText(room.getId());
      
      Element ecken = createXmlListOfPoints("ecken", "Ecke", room.getCorners());
      Element lampen = createXmlListOfPoints("lampen", "Lampe", room.getLamps());
      
      raum.addContent(id);
      raum.addContent(ecken);
      raum.addContent(lampen);
      
      document.setContent(raum);
      
      FileWriter fileWriter = new FileWriter(fileName);
      XMLOutputter xmlOutputter = new XMLOutputter();
      xmlOutputter.setFormat(Format.getPrettyFormat());
      xmlOutputter.output(document, fileWriter);
      
      System.out.println("Saved solution as '" + fileName + "'.");
      
      fileWriter.close();
	}
	
	private Element createXmlListOfPoints(String parentName, String childName, List<Point2D.Float> points) {
		Element parent = new Element(parentName);
    List<Element> childElements = new ArrayList<>();
    
    points.stream().forEach(point -> {
    	Element childElement = new Element(childName);
    	childElement.addContent(new Element("x").setText(String.valueOf(point.x)));
    	childElement.addContent(new Element("y").setText(String.valueOf(point.y)));
    	childElements.add(childElement);
    });
    
    childElements.stream().forEach(childElement -> parent.addContent(childElement));
    
    return parent;
	}
}
