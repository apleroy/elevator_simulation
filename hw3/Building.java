package cscie55.hw3;

public class Building {

	public final static int FLOORS = 7;
	private Elevator elevator;
	private Floor [] arrayOfFloors;
	
	public Building() {
		elevator = new Elevator(this);
		arrayOfFloors = new Floor[FLOORS];
		for (int i = 0; i < FLOORS; i++) {		
			Floor floor = new Floor(this, i + 1); //add 1 to get to the correct floor (can't be floor 0)
			arrayOfFloors[i] = floor;
		}	
	}
	
	public Elevator elevator () {
		return elevator;
	}
	
	public Floor floor (int floorNumber) {
		return arrayOfFloors[floorNumber - 1]; //return Floor object for given floor
	}
	
	public void enter (Passenger passenger) {
		this.floor(1).enterGroundFloor(passenger);
	}
}
