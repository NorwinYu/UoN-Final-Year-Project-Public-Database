package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {


	private List<Ship> ships;               //List of current boards ships
	private List<Result> attacks;   // List of all previous attack attempts

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		this.ships = new ArrayList<Ship>();
		this.attacks = new ArrayList<Result>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		Ship toAdd = new Ship(ship);
		toAdd.setOccupiedSquaresByOrientation(x, y, isVertical);	

		if(toAdd.getShipType() == Ship.ShipType.INVALID) 
			return false;

		// check that each occupied square is valid
		for(Square s : toAdd.getOccupiedSquares()) {
			if(0 > s.getRow() || s.getRow() > 10)
				return false;
			if('A' > s.getColumn() || s.getColumn() > 'J')
				return false;
		}

		// check that there are no shipwise collisions
		// also check that the same board cannot have two of 
		// the same piece
		for(Ship incident : this.ships) {
			if(toAdd.collidesWith(incident))
				return false;
			if(toAdd.getShipType().equals(incident.getShipType()))
				return false;
		}

		this.ships.add(toAdd);

		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	Function handles all attacking , so should
	- See if attack location is valid
	- if we've attacked there before
	- if we hit anything there, and set result accordingly = HIT
	- if that hit sunk the ship =  sunk
	- if that hit sunk the last ship = Surrender
	 */
	public Result attack(int x, char y) {

		Result attackResult = new Result();
		attackResult.setLocation(new Square(x, y));		//
		attackResult.setResult(AtackStatus.MISS);				//should default to Miss if not changed by any others
		//check if valid
		if(x < 0 || x >10 || y < 'A' || y > 'J'){			//if attack attempt is outside bounds of board, set status to invalid
			attackResult.setResult(AtackStatus.INVALID);
			return attackResult;
		}

        for( Result r : attacks){
        	Square s = r.getLocation();
        	if(s.getRow()== x && s.getColumn() == y){
        		attackResult.setResult(AtackStatus.INVALID);
        		return attackResult;
			}
		}

        for (Ship s : ships){								//for each ship
			for (Square sq : s.getOccupiedSquares()) {		//for each square occupied by the current ship
				if(sq.getRow()==x && sq.getColumn()==y){
					attackResult.setResult(AtackStatus.HIT);
					s.takeDamage(x,y);
					if(s.getHealth()==0){
						attackResult.setResult(AtackStatus.SUNK);
						attackResult.setShip(new Ship(s));
					}
					break;
				}
			}
			if(attackResult.getResult()==AtackStatus.HIT || attackResult.getResult()==AtackStatus.SUNK ){
				break;
			}
		}


        int totalHealth = 0;
		for(Ship s : ships){
			totalHealth+= s.getHealth();
		}
		if(totalHealth<=0){
			attackResult.setResult(AtackStatus.SURRENDER);
		}


		this.attacks.add(attackResult);             // add to list of old attack attempts to compare against later
		//TODO check if hit below



		return attackResult;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}

	public List<Result> getAttacks() {
	    //should return all previous attacks
		//TODO implement
		return this.attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
	}
}

