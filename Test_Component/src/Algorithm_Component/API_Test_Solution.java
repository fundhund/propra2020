package Algorithm_Component;

import fernuni.propra.algorithm.*;
import fernuni.propra.file_processing.Room;
import fernuni.propra.test.TestHelper;

import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.geom.Point2D;
import java.util.List;

/*
 * Informationen über das Unit-Testen mit Hilfe von JUnit finden Sie unter http://www.vogella.com/tutorials/JUnit/article.html.
 * In dem dort hinterlegten Dokument sind alle notwendigen Hilfsmittel erläutert.
 * 
 * Designen Sie Ihre Unit-Tests nach dem Arrange-Act-Assert-Prinzip
 */

public class API_Test_Solution {
	
	@Test
	public void validateFileHasToBeValid() {
		// Arrange
		IAusleuchtung api = new Ausleuchtung();
		int expectedNumberOfLamps = 0;
		// Act
		int actualNumberOfLamps = api.solve("", 0);
		// Assert
		assertEquals("Ohne Angabe einer Datei wurde eine zulässige Lösung gefunden.", expectedNumberOfLamps, actualNumberOfLamps);
	}
}
