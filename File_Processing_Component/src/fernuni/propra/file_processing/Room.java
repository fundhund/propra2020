package fernuni.propra.file_processing;

import java.util.List;

public class Room {
	
	private String id;
	private List<Corner> corners;
	private List<Lamp> lamps;
//	private List<Wall>

	public Room(String id, List<Corner> corners, List<Lamp> lamps) {
		this.id = id;
		this.corners = corners;
		this.lamps = lamps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Corner> getCorners() {
		return corners;
	}

	public void setCorners(List<Corner> corners) {
		this.corners = corners;
	}

	public List<Lamp> getLamps() {
		return lamps;
	}

	public void setLamps(List<Lamp> lamps) {
		this.lamps = lamps;
	}
}
