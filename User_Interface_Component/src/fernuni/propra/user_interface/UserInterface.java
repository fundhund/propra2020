package fernuni.propra.user_interface;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import fernuni.propra.file_processing.Room;

/**
 * GUI for rooms.
 * 
 * @author Marius Mielke (4531230)
 *
 */
public class UserInterface extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String mode;
	private Room room;

  public UserInterface(String mode, Room room) {
		this.mode = mode;
		this.room = room;
		
    initUI();
  }

  /**
   * Initializes GUI. 
   */
  private void initUI() {
  	
		String title = createTitle();
		
		RoomView roomView = new RoomView(this.room, 600);
    add(roomView);
    
    this.addComponentListener(new ComponentAdapter( ) {
      public void componentResized(ComponentEvent ev) {
      	float windowMin = Math.min(getWidth(), getHeight());
      	Container contentPane = getContentPane();
      	contentPane.removeAll();
      	JPanel newRoomView = new RoomView(room, windowMin);
      	contentPane.add(newRoomView);
      	setVisible(true);
      	invalidate();
      	repaint();
      }
    });

    setTitle(title);
    this.getContentPane().setBackground(Color.RED);
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  /**
   * Creates window title based on given mode. 
   * 
   * @return title
   */
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
