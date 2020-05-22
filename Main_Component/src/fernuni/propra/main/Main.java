package fernuni.propra.main;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import org.jdom2.JDOMException;

import fernuni.propra.algorithm.Solver;
import fernuni.propra.algorithm.TimeLimitExceededException;
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
			
		HashMap<String, Object> params = getParams(args);
		System.out.println(params);
		
//		String inputFile = (String) params.get("inputFile");
		String inputFile = "instances/validationInstances/Selbsttest_20a.xml";
		
		XmlReader xmlReader = new XmlReader(inputFile);
		Room room = xmlReader.createRoom();
		
		System.out.println(room.getId());
		System.out.println(room.getCorners());
		System.out.println(room.getLamps());
		System.out.println(room.getShape());
		
		String mode = Arrays.stream(args)
				.filter(arg -> arg.startsWith("r="))
				.findFirst()
				.get()
				.split("=")[1];
		
		if (mode.contains("s")) {
			
			int timeLimit = params.containsKey("timeLimit") && params.get("timeLimit") != null 
					? (int) params.get("timeLimit") 
					: 0;
			
			Solver solver = new Solver(room);
			solver.solve(timeLimit);
			room.setLamps(solver.getLampPositions());
		}
		
		if (mode.contains("d")) {
			display(room, mode);
		}
		
		XmlWriter xmlWriter = new XmlWriter(room);
		xmlWriter.writeXml(inputFile.replace(".xml", "_solved.xml"));
	}

	private static void display(Room room, String mode) {
		EventQueue.invokeLater(() -> {
			var ui = new UserInterface(mode, room);
			ui.setVisible(true);
		});
	}
		
	public static HashMap<String, Object> getParams(String[] args) {
		
		// Parameters
		HashMap<String, Boolean> mode = null;
		String inputFile = null;
		Integer timeLimit = null;
		
		// Process command line arguments
		for (String arg : args) {
			
			if (mode == null && arg.matches("r=(s|sd|v|vd|d)")) {
				String modeValue = arg.split("=")[1];
				
				mode = new HashMap<String, Boolean>();
				mode.put("solve", modeValue.contains("s"));
				mode.put("display", modeValue.contains("d"));
				mode.put("validate", modeValue.contains("v"));
						
				continue;
			}
				
			if (inputFile == null && arg.matches("if=.*")) {
				inputFile = arg.split("=")[1];
				continue;
			}
			
			if (timeLimit == null && arg.matches("l=[1-9][0-9]*")) {
				timeLimit = Integer.parseInt(arg.split("=")[1]);
				continue;
			}
		}
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("mode", mode);
		params.put("inputFile", inputFile);
		params.put("timeLimit", timeLimit);
		
		return params;
	}
}
