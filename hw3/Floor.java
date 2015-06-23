package cscie55.hw3;

import java.util.*;

public class Floor {
	
	private int floorNumber;
	private Building building;
	
	private Collection<Passenger> residentPassengers;
	private Collection<Passenger> waitingUpPassengers;
	private Collection<Passenger> waitingDownPassengers;
	
	public Floor (Building building, int floorNumber) {
		this.building = building;
		this.floorNumber = floorNumber;
		residentPassengers = new HashSet<Passenger>();
		waitingUpPassengers = new ArrayList<Passenger>(); //preserves order of entry
		waitingDownPassengers = new ArrayList<Passenger>(); //preserves order of entry
	}
	
	public void waitForElevator (Passenger passenger, int destinationFloor) {
		passenger.waitForElevator(destinationFloor);
		residentPassengers.remove(passenger);
		
		//compare destination to current to determine up/down collection
		if (passenger.destinationFloor() > this.floorNumber) {
			waitingUpPassengers.add(passenger);
		} else {
			waitingDownPassengers.add(passenger);
		}	
	}
	
	Collection<Passenger> waitingUpPassengers () {
		return waitingUpPassengers;
	}
	
	Collection<Passenger> waitingDownPassengers () {
		return waitingDownPassengers;
	}
	
	public boolean isResident (Passenger passenger) {
		//if the passenger is a resident on the floor (not waiting to go up or down)	
		if (residentPassengers.contains(passenger)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void enterGroundFloor (Passenger passenger) {
		//adds a passenger to the floor's residents
		residentPassengers.add(passenger);
	}
	
}
