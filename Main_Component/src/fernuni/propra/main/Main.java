package fernuni.propra.main;

import java.awt.EventQueue;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;

import fernuni.propra.algorithm.Solver;
import fernuni.propra.algorithm.TimeLimitExceededException;
import fernuni.propra.algorithm.Validator;
import fernuni.propra.file_processing.*;
import fernuni.propra.user_interface.UserInterface;

/**
 * Haupteinstiegspunkt der Anwendung.
 *
 * In der Main-Komponente m&uumlssen unter anderem die Eingabeparameter verarbeitet werden.</br>
 * 
 * f&uumlr den Ablaufparameter "r" wird folgende Festlegung getroffen:
 * <ul>
 * <li>"s" (solve): f&uumlr die durch die XML-Datei beschriebene Probleminstanz wird eine L&oumlsung ermittelt. Die Positionen der Lampen werden in der angegebenen XML-Datei gespeichert. Wenn in der XML-Datei bereits eine L&oumlsung enthalten ist, so ist diese zu &uumlberschreiben.</li>
 * <li>"sd" (solve & display): wie "s", nur dass der Raum sowie die ermittelten Positionen der Lampen zus&aumltzlich in der grafischen Oberfl&aumlche gezeigt werden.</li>
 * <li>"v" (validate): durch diese Option wird gepr&uumlft, ob der in der angegebenen XML-Datei enthaltene Raum durch die ebenso dort angegebenen Lampen vollst&aumlndig ausgeleuchtet ist. Das Ergebnis der Pr&uumlfung sowie die Anzahl und Positionen der Lampen werden ausgegeben. Falls die angegebene XML-Datei keinen zul&aumlssigen Raum enth&aumllt, wird eine Fehlermeldung ausgegeben. Die Ausgabe erfolgt in der Kommandozeile.</li>
 * <li>"vd" (validate & display): wie "v", nur dass der Raum und die Lampen nach der Validierung zus&aumltzlich in der grafischen Oberfl&aumlche angezeigt werden.</li>
 * <li>"d" (display): der in der XML-Datei enthaltene Raum und die Lampen werden in der grafischen Oberfl&aumlche angezeigt. Falls die angegebene XML-Datei keinen zul&aumlssigen Raum enth&aumllt, wird eine Fehlermeldung auf der Kommandozeile ausgegeben.</li>
 * </ul>
 * Der Eingabedateiparameter "if" ist ein String, der den Pfad der Eingabedatei beinhaltet.</br>
 * 
 * Der Parameter f&uumlr ein Zeitlimit "l" ist eine positive nat&uumlrliche Zahl, welche die maximale Rechenzeit in Sekunden angibt.
 */
public class Main {

	/**
	 * Haupteinstiegsfunktion
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws IncorrectShapeException 
	 * @throws TimeLimitExceededException 
	 */
	public static void main(String[] args) throws JDOMException, IOException, IncorrectShapeException, TimeLimitExceededException {
			
		HashMap<String, String> params = getParams(args);
		
		String inputFile = (String) params.get("inputFile");
		if (inputFile == null) {
			System.out.println("No input file given. Use if=<FILE_PATH> to pass an input file.");
			return;
		}
		
		String mode = params.get("mode");
		if (mode == null) {
			System.out.println("No run mode given. Use r=s|sd|v|vd|d to pass a run mode (s=solve, v=calidate, d=display).");
			return;
		}
		
		XmlReader xmlReader = new XmlReader(inputFile);
		Room room = xmlReader.createRoom();
		
		if (room == null) return;
		
		if (mode.contains("s")) {
			
			int timeLimit = params.containsKey("timeLimit") && params.get("timeLimit") != null 
					? Integer.parseInt(params.get("timeLimit")) 
					: 0;
			
			Solver solver = new Solver(room);
			solver.solve(timeLimit);
			room.setLamps(solver.getLampPositions());
			
			XmlWriter xmlWriter = new XmlWriter(room);
			xmlWriter.writeXml(inputFile);
		}
		
		if (mode.contains("v")) {
			
			System.out.println("Validating '" + inputFile + "'...");
			
			Validator validator = new Validator(room);
			boolean isValid = validator.validate();
			
			System.out.println("Room configuration is " + (isValid ? "" : "in") + "valid.");
			printLamps(room.getLamps());
		}
		
		if (mode.contains("d")) {
			display(room, mode);
		}
	}
	
	private static void printLamps(List<Point2D.Float> lamps) {
		
		int numberOfLamps = lamps.size();
		if (numberOfLamps == 0) return;
		
		String lampsInfo = "Lamps (" + numberOfLamps + "):";
		
		for (Point2D.Float lamp : lamps) {
			lampsInfo += " [" + lamp.x + ", " + lamp.y + "]";
		}
		
		System.out.println(lampsInfo);
	}

	private static void display(Room room, String mode) {
		EventQueue.invokeLater(() -> {
			var ui = new UserInterface(mode, room);
			ui.setVisible(true);
		});
	}
		
	private static HashMap<String, String> getParams(String[] args) {
		
		// Parameters
		String mode = null;
		String inputFile = null;
		String timeLimit = null;
		
		// Process command line arguments
		for (String arg : args) {
			
			if (mode == null && arg.matches("r=(s|sd|v|vd|d)")) {
				mode = arg.split("=")[1];
				continue;
			}
				
			if (inputFile == null && arg.matches("if=.*")) {
				inputFile = arg.split("=")[1];
				continue;
			}
			
			if (timeLimit == null && arg.matches("l=[1-9][0-9]*")) {
				timeLimit = arg.split("=")[1];
				continue;
			}
		}
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mode", mode);
		params.put("inputFile", inputFile);
		params.put("timeLimit", timeLimit);
		
		return params;
	}
}
