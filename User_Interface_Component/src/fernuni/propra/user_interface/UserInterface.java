package fernuni.propra.user_interface;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fernuni.propra.file_processing.Room;
import fernuni.propra.file_processing.XmlReader;

public class UserInterface extends JFrame {
	
	private String mode;
	private Room room;

  public UserInterface(String mode, Room room) {
		this.mode = mode;
		this.room = room;
    initUI();
  }

  private void initUI() {
  	
		String title = createTitle();
		var icon = new ImageIcon("src/light.png");

    setTitle(title);
    setIconImage(icon.getImage());
    setSize(600, 300);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  private String createTitle() {
  	
  	String titleStart = "Art Gallery Problem";
    	
    	switch(this.mode) {
			case "sd":
				return titleStart + " (Solve & Display)";
			case "d":
				return titleStart + " (Display)";
			case "vd":
				return titleStart + " (Validate & Display)";
			default:
				return titleStart;
		}
  }
}
