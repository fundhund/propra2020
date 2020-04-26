package fernuni.propra.user_interface;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import fernuni.propra.file_processing.Room;

public class UserInterface extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mode;
	private Room room;
	private int width;
	private int height;

  public UserInterface(String mode, Room room) {
		this.mode = mode;
		this.room = room;
		this.width = (int) (Math.ceil(room.getWidth()));
		this.height = (int) (Math.ceil(room.getHeight()));
		
    initUI();
  }

  private void initUI() {
  	
		String title = createTitle();
		var icon = new ImageIcon("src/light.png");
		
		var roomView = new RoomView(this.room);
    add(roomView);

    setTitle(title);
    this.getContentPane().setBackground(Color.RED);
    setIconImage(icon.getImage());
//    setSize(width, height);
    pack();
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
