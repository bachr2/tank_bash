/**
 * 
 */
package calculation;

import tank_bash.test;

/**
 * @author ricu
 * 
 * <h1>tank</h1>
 * calculates movement<br>
 * calculates barrel<br>
 * calculates shot start data<br>
 */
public class tank {
	/**
	 * tank data
	 */
    private tank_data mydata;
    /**
     * shot data
     */
    private shot myshot;
    /**
     * tank got hit
     */
    private boolean got_hit = false;
    /**
     * tank is dead
     */
    private boolean is_dead = false;
    /**
     * tank hits
     */
    private int hit = 0;
    /**
     * tank score
     */
    private int score = 0;
    /**
     * tank steps
     */
    private int steps = 0;
    /**
     * maximal power
     */
    private int MAX_POWER = 160;
    /**
     * minimal power
     */
    private int MIN_POWER = 40;
    /**
     * maximal angle
     */
    private int MAX_ANGLE = 55;
    /**
     * minimal angle
     */
    private int MIN_ANGLE = 15;
    /**
     * maximal steps
     */
    private int MAX_STEPS = 175;
    /**
     * minimal steps
     */
    private int MIN_STEPS = 0;
    /**
     * maximal position x
     */
    private int MAX_X_1 = (int) (test.width / 2 - test.bunker_width / 2);
    /**
     * minimal position x
     */
    private int MIN_X_1 = (int) test.tank_width;
    /**
     * maximal position x
     */
    private int MAX_X_2 = (int) test.width;
    /**
     * minimal position x
     */
    private int MIN_X_2= (int) (test.width / 2 + test.bunker_width / 2 + test.tank_width);
	
    /**
	 * <h1>constructor</h1>
	 * <br>
	 * Standard constructor<br>
	 * 
     * @param width of the tank
     * @param height of the tank
     * @param team of the tank
     * @param player of the tank
     * 
     */
	public tank(double width, double height, int team, int player) {
		this.mydata = new tank_data();
		this.myshot = new shot();
		
		this.mydata.width = width;
		this.mydata.height = height;
		this.mydata.angle = MIN_ANGLE;
		this.mydata.power = MIN_POWER;
		this.mydata.team = team;
		this.mydata.player = player;
		
		if(team==0){
			this.mydata.min_x = MIN_X_1;
			this.mydata.max_x = MAX_X_1;
		}
		else {
			this.mydata.min_x = MIN_X_2;
			this.mydata.max_x = MAX_X_2;
		}
	}
	/**
	 * <h1>set position</h1>
	 * <br>
	 * set the x and y position of the tank<br>
	 * 
	 * @param xpos
	 * @param ypos
	 */
	public void set_pos(double xpos, double ypos) {
		if (xpos - this.mydata.width <= 0) {
			this.mydata.posx = this.mydata.width;
		} else if ((xpos) >= test.width) {
			this.mydata.posx = test.width;
		} else {
			this.mydata.posx = xpos;
		}
		this.mydata.posy = ypos;
	}
	/**
	 * <h1>go left</h1>
	 * <br>
	 * the tank will be moved to the left<br>
	 * when the minimal position x is reached the tank don't move further<br>
	 * the tank only moves if maximal steps isn't reached<br>
	 * 
	 */
	public void go_left() {
		if (steps<MAX_STEPS) {
			if (this.mydata.posx-- - this.mydata.min_x <= 0) {
				this.mydata.posx = this.mydata.min_x;
			} else {
				steps++;
			}
		}
	}
	/**
	 * <h1>go right</h1>
	 * <br>
	 * the tank will be moved to the right<br>
	 * when the maximal position x is reached the tank don't move further<br>
	 * the tank only moves if maximal steps isn't reached<br>
	 * 
	 */
	public void go_right() {
		if (steps<MAX_STEPS) {
			if ((this.mydata.posx++) >= this.mydata.max_x) {
				this.mydata.posx = this.mydata.max_x;
			} else {
				steps++;
			}
		}
	}
	/**
	 * <h1>barrel up</h1>
	 * <br>
	 * moves the angle of the barrel up<br>
	 * when the maximal angle is reached the barrel don't move further<br>
	 * 
	 */
	public void barrel_up() {
		if(this.mydata.angle++>=MAX_ANGLE) {
			this.mydata.angle=MAX_ANGLE;
		}
	}
	/**
	 * <h1>barrel down</h1>
	 * <br>
	 * moves the angle of the barrel down<br>
	 * when the minimal angle is reached the barrel don't move further<br>
	 * 
	 */
	public void barrel_down() {
		if(this.mydata.angle--<=MIN_ANGLE) {
			this.mydata.angle=MIN_ANGLE;
		}
	}
	/**
	 * <h1>power up</h1>
	 * <br>
	 * loads up the power of the shot<br>
	 * when the maximal power is reached it is reseted to minimal power<br>
	 * 
	 */
	public void power_up() {
		if(this.mydata.power++>=MAX_POWER) {
			this.mydata.power = MIN_POWER;
		}
	}
	/**
	 * <h1>shot</h1>
	 * <br>
	 * fires a shot from the actual tank data<br>
	 * 
	 */
	public void shot() {
		myshot.fire_shot(this.mydata.power, this.mydata.angle,
				this.mydata.height + this.mydata.posy, this.mydata.team);
	}
	/**
	 * <h1>get tank data</h1>
	 * <br>
	 * get the tank data structure of the tank<br>
	 * 
	 * @return a structure with the tank data
	 * 
	 */
	public tank_data get_tankdata() {
		return this.mydata;
	}
	/**
	 * <h1>get shot data</h1>
	 * <br>
	 * get the shot data structure of the tank<br>
	 * 
	 * @return a structure with the shot data of this tank
	 * 
	 */
	public shot get_shotdata() {
		return this.myshot;
	}
	/**
	 * <h1>is hit</h1>
	 * <br>
	 * return if the tank got hit or not<br>
	 * 
	 * @return true or false if the tank got hit
	 */
	public boolean is_hit() {
		return got_hit;
	}
	/**
	 * <h1>get hit</h1>
	 * <br>
	 * return the amount the tank got hit<br>
	 * @return the amount of hits
	 */
	public int get_hit() {
		return hit;
	}
	/**
	 * <h1>tank hit</h1>
	 * <br>
	 * hit the tank for one time<br>
	 * checks if the tank is dead<br>
	 * 
	 */
	public void tank_hit() {
		got_hit = true;
		hit++;
		if(hit>=2){
			is_dead = true;
		}
	}
	/**
	 * <h1>add score</h1>
	 * <br>
	 * add the new score points to the score of the tank<br>
	 * 
	 * @param newpoints
	 */
	public void add_score(int newpoints) {
		score+=newpoints;
	}
	/**
	 * <h1>get score</h1>
	 * <br>
	 * get the score of the tank<br>
	 * 
	 * @return score of the tank
	 */
	public int get_score() {
		return score;
	}
	/**
	 * <h1>reset</h1>
	 * <br>
	 * reset the tankdata for the next round<br>
	 * 
	 */
	public void reset() {
		this.mydata.power = MIN_POWER;
		this.steps = MIN_STEPS;
	}
	/**
	 * <h1>is dead</h1>$
	 * <br>
	 * return if the tank is dead or not<br>
	 * 
	 * @return if the tank is dead or not
	 */
	public boolean is_dead() {
		return is_dead;
	}
	/**
	 * <h1>refresh minimum maximum</h1>
	 * <br>
	 * refresh the minimal and maximal value of the position x<br>
	 * 
	 */
	public void refresh_min_max() {
		MAX_X_1 = (int) (test.width / 2 - test.bunker_width / 2);
	    MIN_X_1 = (int) test.tank_width;
	    MAX_X_2 = (int) test.width;
	    MIN_X_2= (int) (test.width / 2 + test.bunker_width / 2 + test.tank_width + test.diff_width);
	    if(this.mydata.team==0){
			this.mydata.min_x = MIN_X_1;
			this.mydata.max_x = MAX_X_1;
		}
		else {
			this.mydata.min_x = MIN_X_2;
			this.mydata.max_x = MAX_X_2;
		}
	}
	
}
