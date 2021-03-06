package application.CTC;
import java.util.*;
import application.TrackModel.*;
public class Schedule{
	private int ID;
	private String Line;
	private String[] Station=new String[2];
	private int Departuretime;
	private int[] ArrivalTime;
	private int[] LeaveTime;
	private int[] distance;
	private int speed;
	Schedule(int TrainID, String myLine, String[] myStation, Integer[] myDistance,int myDeparturetime,int suggestedSpeed,HashMap<String, TrackLine> inputtrack){
		ID=TrainID;
		Line=myLine;
		Station[0]=myStation[0];
		Station[1]=myStation[myStation.length-1];
		distance=Arrays.stream(myDistance).mapToInt(Integer::intValue).toArray();
		Departuretime=myDeparturetime;
		speed=suggestedSpeed;
		ArrivalTime=new int[2];
		LeaveTime=new int[2];
		ArrivalTime[0]=Departuretime-10*60;
		LeaveTime[0]=Departuretime;
		double totallength=0;
		HashMap<String, TrackLine> track = new HashMap<String, TrackLine>();
		track=inputtrack;
		String DepartureStation=myStation[0];
		String StopStation=myStation[myStation.length-1];
		boolean iffindd=false;
		boolean iffinds=false;
		for(String key:track.keySet()) {
			TrackLine tmp=track.get(key);
			try {
				TrackBlock myBlock=tmp.getEntrance();
				TrackBlock next;
				boolean ifA=false;
				TrackJunction possiblenext;
				while (true) {
					if (ifA) {
						possiblenext=myBlock.getJunctionA();
					}
					else {
						possiblenext=myBlock.getJunctionB();
					}
					while (true) {
						if (possiblenext.getID()==-1) {
							next=tmp.getBlock(-1);
							break;
						}
						else if (possiblenext.isSwitch()) {
							TrackSwitch theswitch=tmp.getSwitch(possiblenext.getID());
							if (possiblenext.getEntryPoint()==1||possiblenext.getEntryPoint()==2) {
								possiblenext=theswitch.getMainJunction();
								continue;
							}
							else {
								possiblenext=theswitch.getStraightJunction();
								if(possiblenext.getID()==-1) {
									next=tmp.getBlock(-1);
									break;
								}
								else if(possiblenext.getEntryPoint()==0||tmp.getBlock(possiblenext.getID()).isBidirectional()) {
									continue;
								}
								else {
									possiblenext=theswitch.getDivergingJunction();
									continue;
								}
							}
						}
						else {
							next=tmp.getBlock(possiblenext.getID());
							if (possiblenext.getEntryPoint()==0) {
								ifA=false;
								break;
							}
							else {
								ifA=true;
								break;
							}
						}
					}
					String lala=" "+myBlock.getStationName();
					if (myBlock.getStationName()==null) lala=" ";
					String myblockname="Block "+myBlock.getBlockID()+lala;
					if (myblockname.equals(DepartureStation)) {
						iffindd=true;
					}
					lala=" "+next.getStationName();
					if (next.getStationName()==null) lala=" ";
					String nextblockname="Block "+next.getBlockID()+lala;
					if (nextblockname.equals(StopStation)||next.getBlockID()==-1) {
						iffinds=true;
					}
					if (iffindd) {
						totallength+=myBlock.getLength();
					}
						myBlock=next;
					if(iffinds) {
						break;
					}
				}
				
			} catch (SwitchStateException e) {
			}
			

		}
		ArrivalTime[1]=LeaveTime[0]+(int)(totallength/speed);
		LeaveTime[1]=ArrivalTime[1]+1*60;
		
	}
	public void mergeSchedule(Schedule tmp2){
		int tmpsize=this.getStation().length+tmp2.getStation().length-1;
		int[] distancetmp=new int[tmpsize];
		String[] stationtmp=new String[tmpsize];
		int[] arrivaltmp=new int[tmpsize];
		int[] leavetmp=new int[tmpsize];
		distancetmp[0]=distance[0];
		stationtmp[0]=Station[0];
		arrivaltmp[0]=ArrivalTime[0];
		leavetmp[0]=LeaveTime[0];
		stationtmp[0]=Station[0];
		for (int i=1;i<this.getStation().length;i++){
			distancetmp[i]=distance[i-1];
			stationtmp[i]=Station[i];
			arrivaltmp[i]=ArrivalTime[i];
			leavetmp[i]=LeaveTime[i];
			stationtmp[i]=Station[i];
		}
		distancetmp[this.getStation().length-1]=tmp2.getDistance()[0];
		leavetmp[this.getStation().length-1]=tmp2.getDepartureTime();
		for (int i=this.getStation().length;i<tmpsize;i++){
			arrivaltmp[i]=leavetmp[i-1]+tmp2.getArrivalTime()[i-this.getStation().length+1]-tmp2.getLeaveTime()[i-this.getStation().length];
			leavetmp[i]=arrivaltmp[i]+1*60;
			distancetmp[i]=tmp2.getDistance()[i-this.getStation().length];
			stationtmp[i]=tmp2.getStation()[i-this.getStation().length+1];
		}
		Station=stationtmp;
		ArrivalTime=arrivaltmp;
		LeaveTime=leavetmp;
		distance=distancetmp;
		
	}
	public ArrayList<String> printschedule(){
		
		ArrayList<String> tmp=new ArrayList<String>();
		tmp.add(String.valueOf(ID));
		tmp.add("	"+Line);
		for (int i=0;i<Station.length;i++) {
			//tmp.add("	"+Station[i]+" "+"Arrival at "+ArrivalTime[i]+". Leave at "+LeaveTime[i]);//TODO 
			tmp.add("	"+Station[i]+" "+"Arrival at "+((ArrivalTime[i] % 86400) / 3600)+":"+(((ArrivalTime[i] % 86400) % 3600) / 60)+". Leave at "+((LeaveTime[i] % 86400) / 3600)+":"+(((LeaveTime[i] % 86400) % 3600) / 60));//TODO 
		}
		return tmp;

	}
	public int getID(){
		return ID;
	}
	public String getLine(){
		return Line;
	}
	public String[] getStation(){
		return Station;
	}
	public int[] getArrivalTime(){
		return ArrivalTime;
	}
	public int[] getLeaveTime(){
		return LeaveTime;
	}
	public int[] getDistance(){
		return distance;
	}
	public int getDepartureTime(){
		return Departuretime;
	}
	public int getSpeed(){
		return speed;
	}
}