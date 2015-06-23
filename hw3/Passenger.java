package cscie55.hw3;

public class Passenger {

	private static final int UNDEFINED_FLOOR = -1;
	private int currentFloor;
	private int destinationFloor;
	private int passengerID;
	
	public Passenger (int passengerID) {
		this.passengerID = passengerID;
		currentFloor = 1;
		destinationFloor = UNDEFINED_FLOOR;
	}
	
	public int currentFloor () {
		return currentFloor;
	}
	
	public int destinationFloor () {
		return destinationFloor;
	}
	
	public void waitForElevator (int newDestinationFloor) {
		destinationFloor = newDestinationFloor;
	}
	
	public void boardElevator () {
		currentFloor = UNDEFINED_FLOOR;
	}
	
	public void arrive () {
		currentFloor = destinationFloor;
		destinationFloor = UNDEFINED_FLOOR;
	}
	
	public String toString () {
		String s = ("Passenger: " + passengerID + ", currentFloor = " + currentFloor() + ", destinationFloor = " + destinationFloor());
		return s;
	}
	
	int passengerID () {
		return passengerID;
	}
	
}
