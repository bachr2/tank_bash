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
	
    private tank_data mydata;
    private shot myshot;
    
    private boolean got_hit = false;
    private boolean is_dead = false;
    private int hit = 0;
    private int score = 0;
    private int steps = 0;
    
    private int MAX_POWER = 160;
    private int MIN_POWER = 40;
    private int MAX_ANGLE = 55;
    private int MIN_ANGLE = 15;
    private int MAX_STEPS = 150;
    private int MIN_STEPS = 0;
	
    /**
	 * <h1>constructor</h1>
	 * <br>
	 * Standard constructor<br>
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
	}
	
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
	
	public void go_left() {
		if (steps<MAX_STEPS) {
			if (this.mydata.posx-- - this.mydata.width <= 0) {
				this.mydata.posx = this.mydata.width;
			} else {
				steps++;
			}
		}
	}
	
	public void go_right() {
		if (steps<MAX_STEPS) {
			if ((this.mydata.posx++) >= test.width) {
				this.mydata.posx = test.width;
			} else {
				steps++;
			}
		}
	}
	
	public void barrel_up() {
		if(this.mydata.angle++>=MAX_ANGLE) {
			this.mydata.angle=MAX_ANGLE;
		}
	}
	
	public void barrel_down() {
		if(this.mydata.angle--<=MIN_ANGLE) {
			this.mydata.angle=MIN_ANGLE;
		}
	}
	
	public void power_up() {
		if(this.mydata.power++>=MAX_POWER) {
			this.mydata.power = MIN_POWER;
		}
	}
	
	public void shot() {
		myshot.fire_shot(this.mydata.power, this.mydata.angle,
				this.mydata.height + this.mydata.posy, this.mydata.team);
	}
	
	public tank_data get_tankdata() {
		return this.mydata;
	}

	public shot get_shotdata() {
		return this.myshot;
	}
	
	public boolean is_hit() {
		return got_hit;
	}
	
	public int get_hit() {
		return hit;
	}
	
	public void tank_hit() {
		got_hit = true;
		hit++;
		if(hit>=2){
			is_dead = true;
		}
	}

	public void add_score(int calc_hit) {
		score+=calc_hit;
	}
	
	public int get_score() {
		return score;
	}

	public void reset() {
		this.mydata.power = MIN_POWER;
		this.steps = MIN_STEPS;
	}
	public boolean is_dead() {
		return is_dead;
	}
	
}
