/**
 * 
 */
package calculation;

import java.util.ArrayList;

import tank_bash.test;

/**
 * @author ricu
 * 
 * <h1>tank</h1>
 * check hit collision<br>
 */
public class hit {
	
	private ArrayList<tank> mytanks;
	int[][] mymap = null;
	
	/**
	 * <h1>constructor</h1>
	 * <br>
	 * Standard constructor<br>
	 * 
	 */
	public hit() {
		mytanks = new ArrayList<tank>();
	}
	/**
	 * <h1>add tank</h1>
	 * <br>
	 * add tank on the map<br>
	 * 
	 * @param tank the tank object to add
	 * 
	 */
	public void add_tank(tank mytank) {
		mytanks.add(mytank);
	}
	/**
	 * <h1>hit calculation</h1>
	 * <br>
	 * calculate if the shot hit anything on the map<br>
	 * 
	 * @param shot the shot to calculate the hit
	 * @param player int which taplayer shot
	 * 
	 * @return int what got hit
	 */
	public int calc_hit(shot shot, int player) {
		tank myshooter = mytanks.get(player);
		ArrayList<shot_data> shotdata = shot.get_shotdata();
		mymap = new int[(int) test.width][(int) test.height];
		// create map
		for(int i = 0; i < (int) test.width; i++){
			for(int j = 0; j < (int) test.height; j++) {
				// ground
				if(j<20){
					mymap[i][j] = 0xF;
				}
				// bunker
			}
		}
		// add easer egg
		// add tanks to map
		for(int i = 0; i < mytanks.size(); i++) {
			tank mytank = mytanks.get(i);
			for(int j = 0; j < mytank.get_tankdata().width; j++) {
				for(int k = 0; k < mytank.get_tankdata().height; k++) {
					int x = (int) (mytank.get_tankdata().posx - j);
					int y = (int) (mytank.get_tankdata().posy + k);
					mymap[x][y] = i+1;
				}
			}
		}
		// check if shot hit
		for(int i = 0; i < shotdata.size(); i++) {
			shot_data myshot = shotdata.get(i);
			int shotx = (int) (myshooter.get_tankdata().posx - myshooter.get_tankdata().width / 2 + myshot.width);
			int shoty = (int) (myshot.height) ;
			if(shotx<mymap.length && shotx>=0)
			{
				if (shoty<mymap[shotx].length && shoty>=0)
				{
					if(mymap[shotx][shoty]>0) {
						System.out.println("shot: x: "+shotx+" y: "+shoty+" map: "+mymap[shotx][shoty]);
						shotdata.get(i).hit=true;
						return mymap[shotx][shoty];
					}
				}
			}
		}
		return 0;
	}

}
