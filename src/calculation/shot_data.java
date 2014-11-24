/**
 * 
 */
package calculation;

/**
 * @author ricu
 * 
 * <h1>shot data structure</h1>
 * contains all shot data<br>
 */
public class shot_data {
	/**
	 * width the shot set back until now
	 */
	public double width = 0;
	/**
	 * height the shot have now
	 */
	public double height = 0;
	/**
	 * the flight time of the shot at this point
	 */
	public double time = 0;
	/**
	 * from which team is the shot fired
	 */
	public int team = 0;
	/**
	 * the angle of the shot
	 */
	public double tempangle;
	/**
	 * has the shot hit at this point?
	 */
	public boolean hit;
}
