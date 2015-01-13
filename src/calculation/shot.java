/**
 * 
 */
package calculation;

import java.util.ArrayList;

import data.shot_data;

/**
 * @author ricu
 * 
 * <h1>tank</h1>
 * calculate shot data<br>
 */
public class shot {
	/**
	 * time step
	 */
    private double step=.01;
    /**
     * my shot data
     */
    private ArrayList<shot_data> mydata;
    
    /**
	 * <h1>constructor</h1>
	 * <br>
	 * Standard constructor<br>
	 * 
	 */
    public shot()
    {
        
        mydata = new ArrayList<shot_data>();
    	
    }
    /**
	 * <h1>hit calculation</h1>
	 * <br>
	 * calculate if the shot hit anything on the map<br>
	 * 
	 * @param v double of the start speed
	 * @param angle double of the start angle
	 * @param height double of the start height
	 * @param team int which team
	 * 
	 */
    public void fire_shot(double v, double angle, double height, int team)
    {
    	
        double i=0.0, x=0.0, y=1.0, vx, vy, maxY=0.0, a=0.0;
        final int fixed_angle = 100;
        
        mydata.clear();
        
        vx = v * (Math.cos(angle * (3.1416 / 180)));
        vy = v * (Math.sin(angle * (3.1416 / 180)));
        		
        while(y>0.0)
        {
        	mydata.add(new shot_data());
            i+= step;
            x = vx*i;
            y = (-(5 * (i*i)) + (vy * i)) + height + 2;
            
            if(y>maxY)
            {
                maxY=y;
                if(i<fixed_angle){
                	a=Math.toDegrees(Math.atan(y/x));
                }
                else if(i==fixed_angle) {
                	a=Math.toDegrees(Math.atan(y/x));
                	for(int j = 0; j < mydata.size(); j++){
                		 mydata.get(j).tempangle=a;
                	}
                }
                else {
                	a=Math.toDegrees(Math.atan(y/x));
                }
            }
            else
            {
            	if(i<fixed_angle){
                	a=Math.toDegrees(Math.atan(y/x));
                }
                else if(i==fixed_angle) {
                	a=Math.toDegrees(Math.atan(y/x));
                	for(int j = 0; j < mydata.size(); j++){
                		 mydata.get(j).tempangle=a;
                	}
                }
                else {
                	a=Math.toDegrees(Math.atan(y/x));
                }
            }
			if (team==1) {
				x *= -1;
			}
			mydata.get(mydata.size()-1).width = x;
			mydata.get(mydata.size()-1).height=y;
            mydata.get(mydata.size()-1).time=i;
            mydata.get(mydata.size()-1).team=team;
            mydata.get(mydata.size()-1).tempangle=a;
            mydata.get(mydata.size()-1).hit=false;
        }
        
    }
    /**
	 * <h1>get shot data</h1>
	 * <br>
	 * returns the calculated shot data<br>
	 * 
	 * @return ArrayList of shot_data
	 */
	public ArrayList <shot_data> get_shotdata() {
		
		return mydata;
		
	}
}
