package cscie55.hw3;

import java.util.*;

public class Elevator {

public static final int CAPACITY = 10;
	
	private int currentFloor; 
	private String directionOfTravel;
	private boolean goingUp;
	private boolean goingDown;
	private Building building;
	
	private Set<Passenger> allPassengers;
	
	//for each floor we need a collection of Passengers who intend to disembark on that floor
	private Map<Floor, HashSet<Passenger>> disembarkingPassengers;
	
	public Elevator (Building building) {
		this.building = building;
		currentFloor = 0; //floors correspond to array index position
		directionOfTravel = "UP";
		goingUp = true;
		goingDown = false;
		allPassengers = new HashSet<Passenger>();
		disembarkingPassengers = new HashMap<Floor, HashSet<Passenger>>();		
	}
		
	public void move () {	
		//1 - Check direction and move up or down
		checkDirection (currentFloor);
		if (goingUp()) {
			currentFloor++;
		} else {
			currentFloor--;
		}
		
		//2 - Unload Passengers with destination of the current floor
		unloadPassengers();
		
		//3 - Check direction again after movement to determine collection to board
		checkDirection(currentFloor);
		
		//4 - If heading Up, board all the waitingUpPassengers for that Floor else board waitingDownPassengers
		try {
			if (goingUp()) {
				//while collection has passengers, iterate through collection and board passenger
				Iterator<Passenger> iterator = building.floor(this.currentFloor()).waitingUpPassengers().iterator();		
				while (iterator.hasNext()) {
					Passenger p = iterator.next();
					iterator.remove();
					boardPassenger(p);
				}		
			} else {			
				Iterator<Passenger> iterator = building.floor(this.currentFloor()).waitingDownPassengers().iterator();		
				while(iterator.hasNext()){
					Passenger p = iterator.next();
					iterator.remove();
					boardPassenger(p);	
				}			
			}
		} catch (ElevatorFullException e) {
			//no need to perform action - elevator will come back to remaining passengers
		}
	}
	
	public void boardPassenger (Passenger passenger) throws ElevatorFullException {
		
		if (passengerCount() < Elevator.CAPACITY) {
			allPassengers.add(passenger);
			
			if (goingUp()) {
				building.floor(this.currentFloor()).waitingUpPassengers().remove(passenger);
			}
			else if (goingDown()) {
				building.floor(this.currentFloor()).waitingDownPassengers().remove(passenger);
			}
			
			//put the passenger in the correct set determined by their intended destination
			Floor df = building.floor(passenger.destinationFloor());
			HashSet<Passenger> set = disembarkingPassengers.get(df);
			
			if (set == null) { //if no set of passengers exists, put one in for the Destination Floor
				set = new HashSet<Passenger>();
				disembarkingPassengers.put(df, set);
			}
			set.add(passenger);
			
		} else {
			//before throwing exception - add back the passenger that the iterator removed and passed to this method
			if (goingUp()) {
				building.floor(this.currentFloor()).waitingUpPassengers().add(passenger);
			}
			else if (goingDown()) {
				building.floor(this.currentFloor()).waitingDownPassengers().add(passenger);
			}
			throw new ElevatorFullException ();
		}
		
	}
	
	public String toString () {
		String s = ("Floor: " + currentFloor() + ": " + passengerCount() + " passengers");
		return s;
	}
	
	public Set<Passenger> passengers () {
		return allPassengers;
	}
	
	public int currentFloor () {
		return currentFloor + 1;
	}
	
	private int passengerCount () {
		return allPassengers.size();
	}
	
	private void unloadPassengers () {
		//Empty the disembarkingPassengers set for this Floor object
		HashSet<Passenger> set = disembarkingPassengers.get(building.floor(this.currentFloor()));
		
		if(set == null) {
			//prevent null pointer
		} else {
			//for each passenger, remove it from Set and place it in this floors resident collection (enterGroundFloor)
			Iterator<Passenger> iterator = set.iterator();
			while(iterator.hasNext()){
				Passenger p = iterator.next();
				iterator.remove();
				set.remove(p);
				allPassengers.remove(p);
				building.floor(this.currentFloor()).enterGroundFloor(p);
			}	
		}
	}
		
	public boolean goingUp () {
		return goingUp;
	}
	
	public boolean goingDown () {
		return goingDown;
	}
	
	private void checkDirection (int floor) {
		if (directionOfTravel.equals("UP") && floor == (Building.FLOORS - 1)) {
			directionOfTravel = "DOWN";
			goingUp = false;
			goingDown = true;
		}
		else if (directionOfTravel.equals("DOWN") && floor == 0) {
			directionOfTravel = "UP";
			goingUp = true;
			goingDown = false;
		}
	}
}
