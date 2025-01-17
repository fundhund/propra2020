package Algorithm_Component;

import fernuni.propra.algorithm.*;
import org.junit.Test;
import static org.junit.Assert.*;

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
	
	@Test
	public void solves20a() {
		// Arrange
		IAusleuchtung api = new Ausleuchtung();
		int expectedNumberOfLamps = 2;
		// Act
		int actualNumberOfLamps = api.solve("instances/validationInstances/Selbsttest_20a.xml", 0);
		// Assert
		assertEquals("Inkorrekte Lösung ausgegeben.", expectedNumberOfLamps, actualNumberOfLamps);
	}
	
	@Test
	public void solves20b() {
		// Arrange
		IAusleuchtung api = new Ausleuchtung();
		int expectedNumberOfLamps = 2;
		// Act
		int actualNumberOfLamps = api.solve("instances/validationInstances/Selbsttest_20b.xml", 0);
		// Assert
		assertEquals("Inkorrekte Lösung ausgegeben.", expectedNumberOfLamps, actualNumberOfLamps);
	}
	
	@Test
	public void solves20c() {
		// Arrange
		IAusleuchtung api = new Ausleuchtung();
		int expectedNumberOfLamps = 1;
		// Act
		int actualNumberOfLamps = api.solve("instances/validationInstances/Selbsttest_20c.xml", 0);
		// Assert
		assertEquals("Inkorrekte Lösung ausgegeben.", expectedNumberOfLamps, actualNumberOfLamps);
	}
	
	@Test
	public void solves100a() {
		// Arrange
		IAusleuchtung api = new Ausleuchtung();
		int expectedNumberOfLamps = 17;
		// Act
		int actualNumberOfLamps = api.solve("instances/validationInstances/Selbsttest_100a.xml", 0);
		// Assert
		assertEquals("Inkorrekte Lösung ausgegeben.", expectedNumberOfLamps, actualNumberOfLamps);
	}
	
	@Test
	public void solves100b() {
		// Arrange
		IAusleuchtung api = new Ausleuchtung();
		int expectedNumberOfLamps = 16;
		// Act
		int actualNumberOfLamps = api.solve("instances/validationInstances/Selbsttest_100b.xml", 0);
		// Assert
		assertEquals("Inkorrekte Lösung ausgegeben.", expectedNumberOfLamps, actualNumberOfLamps);
	}
}
