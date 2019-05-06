package instance;

import java.util.ArrayList;
import characters.Entity;
import display.Display;

public abstract class Instance {
	public boolean awaitingInput;
	protected ArrayList<ArrayList<Entity>> team = new ArrayList<ArrayList<Entity>>(); //array for teams that holds array for Entitys
	protected ArrayList<Entity> initiativeList = new ArrayList<Entity>();
	protected RPGAction currentAction;
	protected int currentTeam;

	public ArrayList<Entity> getInitiativeList() {
		return initiativeList;
	}
	public ArrayList<ArrayList<Entity>> getTeams() {
		return team;
	}
	public int getCurrentTeam() {
		return currentTeam;
	}
	public Entity getEntity(int i, int teamNum) { //unclear why anything would request this as of yet.
		return team.get(teamNum).get(i);
	}

	public void addEntity(Entity add, int teamNum) { // Add people to the instance.
		if (!initiativeList.contains(add)) {
			if (teamNum>(team.size()-1))
				for (int i=0; i<=(teamNum-(team.size()-1)); i++)
					team.add(new ArrayList<Entity>());
			team.get(teamNum).add(add);


			// Adds sorted to IntiativeList.
			// TODO Highly suspect I messed up the math here. Has not been confirmed.
			boolean found = false;
			int index=0;
			while(index <= initiativeList.size() && !found) { // Someone check this and remove this note or tell me. I'm not sure about this.
				//Display.debug("Checking index " + index + "... ");
				if (initiativeList.size() != 0) { // Checks to make sure something is already here.
					if (add.getPerception() > initiativeList.get(index).getPerception()) {
						//Display.debug("Greater than current index... ");
						if (initiativeList.size() > index+1) {
							if (!(add.getPerception() <= initiativeList.get(index+1).getPerception())) {
								//Display.debug("Less than or equal to next. ");
								Display.debug(add.getName() + " was added to instance. After.");
								initiativeList.add(index+1, add);
								found = true;
							}
						} else {initiativeList.add(index+1, add); found = true; Display.debug(add.getName() + " was added to instance. Last.");}
					} else {initiativeList.add(index, add); found = true; Display.debug(add.getName() + " was added to instance. Before.");}
				} else {initiativeList.add(add); found = true; Display.debug(add.getName() + " was added to instance. First in.");}
				index++;
			}
			//initiativeList.add(add); // Temp
		} 
	}

	public abstract void launch();

	public abstract boolean checkActive();

}
