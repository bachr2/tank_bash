/**
 * 
 */
package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tank_bash.test;
import calculation.shot;
import calculation.shot_data;
import calculation.tank;

/**
 * @author ricu
 *
 * <h1>draw the game</h1>
 * all the graphical code is inside this class<br>
 */
@SuppressWarnings("serial")
public class draw_game extends JPanel{
	/**
	 * Array of tanks
	 */
	private tank[] mytanks;
	/**
	 * > 0 if shoting
	 */
	private int shoting = -1;
	/**
	 * shot data
	 */
	private shot theshot = null;
	/**
	 * shot counter
	 */
	private int shot_Counter = 0;
	/**
	 * show muzzle flash
	 */
	private int flashing = -1;
	/**
	 * amount of trees in the game
	 */
	private int amount_tree = -1;
	/**
	 * amount of buildings in the game
	 */
	private int amount_buildings = -1;
	/**
	 * amount of clouds in the game
	 */
	private int amount_clouds = -1;
	/**
	 * amount of stars in the game
	 */
	private int amount_stars = -1;
	/**
	 * daytime state
	 */
	private int daytime = -1;
	/**
	 * Array for sorts of trees
	 */
	private int[] tree_x = null;
	/**
	 * Array for sorts of houses
	 */
	private int[] house_x = null;
	/**
	 * Array for elements of houses
	 */
	private int[] house_elements = null;
	/**
	 * Array for y of clouds
	 */
	private int[] cloud_y = null;
	/**
	 * Array for x of clouds
	 */
	private int[] cloud_x = null;
	/**
	 * background color
	 */
	private Color mybgc = null;
	/**
	 * background color1
	 */
	private Color mybgc1 = null;
	/**
	 * ground color dark
	 */
	private Color mygdc = null;
	/**
	 * ground color hell
	 */
	private Color myghc = null;
	/**
	 * Rectangle for bullet
	 */
	private Rectangle bullet = null;
	/**
	 * Rectangles for tanks
	 */
	private Rectangle[] tank = null;
	/**
	 * Rectangles for barrels
	 */
	private Rectangle[] barrel = null;
	/**
	 * Rectangle for ground
	 */
	private Rectangle ground_d = null;
	/**
	 * Rectangle for ground
	 */
	private Rectangle ground_h = null;
	/**
	 * Rectangle for bunker
	 */
	private Rectangle bunker = null;
	/**
	 * Rectangles for trees
	 */
	private Rectangle[] tree = null;
	/**
	 * Rectangles for clouds
	 */
	private Rectangle[] cloud = null;
	/**
	 * Rectangles for trees
	 */
	private Rectangle[] house = null;
	/**
	 * Image tanks
	 */
	private ImageIcon myimg[] = null;
	/**
	 * Image tanks
	 */
    private Image img_tank[] = null;
	/**
	 * Image barrel
	 */
    private ImageIcon myimg1[] = null;
	/**
	 * Image barrel
	 */
    private Image img_barrel[] = null;
	/**
	 * Image bullet
	 */
    private ImageIcon myimg2 = null;
	/**
	 * Image bullet
	 */
    private Image img_bullet = null;
	/**
	 * Image muzzle flash
	 */
    private ImageIcon myimg3 = null;
	/**
	 * Image muzzle flash
	 */
    private Image img_muzfl = null;
	/**
	 * Image explosions
	 */
    private ImageIcon myimg4 = null;
	/**
	 * Image explosions
	 */
    private Image img_explosions = null;
	/**
	 * Image bunker
	 */
    private ImageIcon myimg5 = null;
	/**
	 * Image bunker
	 */
    private Image img_bunker = null;
    /**
	 * Image trees
	 */
    private ImageIcon myimg6[] = null;
	/**
	 * Image trees
	 */
    private Image img_tree[] = null;
    /**
	 * Image house
	 */
    private ImageIcon myimg7[] = null;
	/**
	 * Image house
	 */
    private Image img_house[] = null;
    /**
	 * Image top of house
	 */
    private ImageIcon myimg8[] = null;
	/**
	 * Image top of house
	 */
    private Image img_top[] = null;
    /**
   	 * Image clouds
   	 */
       private ImageIcon myimg9[] = null;
   	/**
   	 * Image clouds
   	 */
       private Image img_cloud[] = null;
    /**
     * Point of explosion
     */
    private Point expl = null;
    /**
     * state of explosion
     */
    private int expl_state = -1;
    /**
     * actual player
     */
    private int act_player = -1;
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		drawDesignNotHitable_b(g2d);
		drawDesignHitable(g2d);
		for(int i = act_player+mytanks.length; i >= act_player; i--) {
			drawShot(g2d,i%mytanks.length);
			drawTank(g2d,i%mytanks.length);
		}
		drawExplosion(g2d);
		drawDesignNotHitable_f(g2d);
		for(int i = act_player+mytanks.length; i >= act_player; i--) {
			drawLabel(g2d,i%mytanks.length);
		}
    }
	/**
	 * <h1>fire a shot</h1>
	 * <br>
	 * allow the draw process of the shot<br>
	 * saves the data of the shot<br>
	 * 
	 * @param myshot the shot to draw
	 * @param tanknr integer which tank
	 */
	public void shot(shot myshot, int tanknr) {
		shoting = tanknr;
		theshot = myshot;
	}
	/**
	 * <h1>draw the explosions</h1>
	 * <br>
	 * draws the different explosions<br>
	 * 
	 * @param g2d Graphics to show the objects
	 */
	private void drawExplosion(Graphics2D g2d) {
		if(expl!=null&&expl_state!=-1){
			if(expl_state>24){
				expl_state=-1;
				expl=null;
			}
			else {
				//img - the specified image to be drawn. This method does nothing if img is null.
			    //dx1 - the x coordinate of the first corner of the destination rectangle.
			    //dy1 - the y coordinate of the first corner of the destination rectangle.
			    //dx2 - the x coordinate of the second corner of the destination rectangle.
			    //dy2 - the y coordinate of the second corner of the destination rectangle.
			    //sx1 - the x coordinate of the first corner of the source rectangle.
			    //sy1 - the y coordinate of the first corner of the source rectangle.
			    //sx2 - the x coordinate of the second corner of the source rectangle.
			    //sy2 - the y coordinate of the second corner of the source rectangle.
			    //observer - object to be notified as more of the image is scaled and converted.
				g2d.drawImage(img_explosions, expl.x-20, expl.y+20, expl.x+20, expl.y-20, expl_state*40, 0, expl_state*40+40, 40, this);
				expl_state++;
			}
		}
	}
	/**
	 * <h1>create a star shape</h1>
	 * <br>
	 * creating a star with swing<br>
	 * 
	 * @param arms amount of arms
	 * @param center of the star
	 * @param rOuter outer radius
	 * @param rInner inner radius
	 * @return shape of a star
	 */
	public static Shape createStar(int arms, Point center, double rOuter, double rInner)
	{
	    double angle = Math.PI / arms;

	    GeneralPath path = new GeneralPath();

	    for (int i = 0; i < 2 * arms; i++)
	    {
	        double r = (i & 1) == 0 ? rOuter : rInner;
	        Point2D.Double p = new Point2D.Double(center.x + Math.cos(i * angle) * r, center.y + Math.sin(i * angle) * r);
	        if (i == 0) path.moveTo(p.getX(), p.getY());
	        else path.lineTo(p.getX(), p.getY());
	    }
	    path.closePath();
	    return path;
	}
	/**
	 * <h1>draw the design</h1>
	 * <br>
	 * draws the not hitable part of the world design<br>
	 * 
	 * @param g2d Graphics to show the objects
	 */
	private void drawDesignNotHitable_b(Graphics2D g2d) {
		Color mycolor = g2d.getColor();
		// top gradient
		GradientPaint gp = new GradientPaint(0, 0, mybgc1, 0, 100, mybgc);
        g2d.setPaint(gp);
		g2d.drawRect(0, 0, (int) test.width, 100);
		g2d.fillRect(0, 0, (int) test.width, 100);
		// sun
		if(daytime==0){
			// TODO dawn & half sun
		}
		else if(daytime==1){
			// TODO full sun
		}
		else if(daytime==2){
			// TODO sunset & half sun
		}
		// moon & stars
		if(daytime==0){
			// TODO few stars
		}
		else if(daytime==2){
			// TODO few stars
		}
		else if(daytime==3){
			// TODO many stars & moon
		}
		// buildings
		for(int i = 0; i < amount_buildings; i++){
			house[i].setLocation(house_x[i],(int) (test.height-(test.ground1_height+test.ground2_height+house[i].height)));
			for(int j = 0; j < house_elements[i]; j++){
				g2d.drawImage(img_house[i], house[i].x, img_top[i].getHeight(null)+(img_house[i].getHeight(null)*j)+house[i].y, this);
			}
			g2d.drawImage(img_top[i], house[i].x, house[i].y, this);
		}
		// trees
		for(int i = 0; i < amount_tree*2/3; i++){
			tree[i].setLocation(tree_x[i],(int) (test.height-(test.ground1_height+test.ground2_height+tree[i].height)));
			g2d.drawImage(img_tree[i], tree[i].x, tree[i].y, this);
		}
		// clouds
		Random myrandom = new Random();
		for(int i = 0; i < amount_clouds*2/3; i++){
			cloud_x[i]+=1;
			cloud[i].setLocation(cloud_x[i],cloud_y[i]);
			g2d.drawImage(img_cloud[i], cloud[i].x, cloud[i].y, this);
			if(cloud_x[i]>(int)test.width){
				cloud_x[i] = myrandom.nextInt()%((int)test.width*2);
				if(cloud_x[i] > 0){
					cloud_x[i]*=-1;
				}
			}
		}
		// TODO birds
		g2d.setColor(mycolor);
	}
	/**
	 * <h1>draw the design</h1>
	 * <br>
	 * draws the not hitable part of the world design<br>
	 * 
	 * @param g2d Graphics to show the objects
	 */
	private void drawDesignNotHitable_f(Graphics2D g2d) {
		Color mycolor = g2d.getColor();
		// trees
		for(int i = amount_tree*2/3; i < amount_tree; i++){
			tree[i].setLocation(tree_x[i],(int) (test.height-(test.ground1_height+tree[i].height)));
			g2d.drawImage(img_tree[i], tree[i].x, tree[i].y, this);
		}
		// clouds
		Random myrandom = new Random();
		for(int i = amount_clouds*2/3; i < amount_clouds; i++){
			cloud_x[i]+=2;
			cloud[i].setLocation(cloud_x[i],cloud_y[i]);
			g2d.drawImage(img_cloud[i], cloud[i].x, cloud[i].y, this);
			if(cloud_x[i]>(int)test.width){
				cloud_x[i] = myrandom.nextInt()%((int)test.width*2);
				if(cloud_x[i] > 0){
					cloud_x[i]*=-1;
				}
			}
		}
		// TODO birds
		g2d.setColor(mycolor);
	}
	/**
	 * <h1>draw the design</h1>
	 * <br>
	 * draws the hitable part of the world design<br>
	 * 
	 * @param g2d Graphics to show the objects
	 */
	private void drawDesignHitable(Graphics2D g2d) {
		
		Color mycolor = g2d.getColor();
		g2d.setFont(new Font("Purisa", Font.BOLD, 12));
		// ground
		g2d.setColor(mygdc);
		ground_d.setBounds(0,
				(int) (test.height-test.ground1_height),
				(int) test.ground1_width,
				(int) test.ground1_height
		);
		g2d.fill(ground_d);
		g2d.setColor(myghc);
		ground_h.setBounds(0,
				(int) (test.height-(test.ground1_height+test.ground2_height)),
				(int) test.ground2_width,
				(int) test.ground2_height
		);
		g2d.fill(ground_h);
		// bunker
		bunker.setBounds((int) (test.width/2-test.bunker_width/2),
				(int) (test.height-(test.ground1_height+test.ground2_height+test.bunker_height)),
				(int) test.bunker_width,
				(int) test.bunker_height
		);
		g2d.drawImage(img_bunker,bunker.x,bunker.y,this);
		// TODO easteregg
		g2d.setColor(mycolor);
	}
	/**
	 * <h1>draw the shot</h1>
	 * <br>
	 * draws the shot which was fired<br>
	 * 
	 * @param g2d Graphics to show the objects
	 * @param tanknr int which tank
	 */
	private void drawShot(Graphics2D g2d, int tanknr) {
		if (shoting==tanknr) {
			ArrayList<shot_data> mydata = theshot.get_shotdata();
			AffineTransform orig = g2d.getTransform();
			AffineTransform at;
			
			if (shot_Counter < mydata.size()) {
				int x = 0;
				int y = 0;
				if (tanknr%2==0) {
					// x = posx - (width / 2)
					int posx = (int) (mytanks[tanknr].get_tankdata().posx - mytanks[tanknr].get_tankdata().width / 2 + mydata.get(shot_Counter).width);
					x = posx;
					// y = maxy - height
					y = (int) (this.getHeight() - 5 - (int) mydata.get(shot_Counter).height);
					at= AffineTransform.getRotateInstance(Math.toRadians(-mydata.get(shot_Counter).tempangle),x, y);
				}
				else {
					// x = posx - (width / 2)
					int posx = (int) (mytanks[tanknr].get_tankdata().posx - mytanks[tanknr].get_tankdata().width / 2 + mydata.get(shot_Counter).width);
					x = posx - 5;
					// y = maxy - height
					y = (int) (this.getHeight() - 5 - (int) mydata.get(shot_Counter).height);
					at= AffineTransform.getRotateInstance(Math.toRadians(mydata.get(shot_Counter).tempangle+180),x, y);
				}
				
				at.createTransformedShape(barrel[tanknr]);
				g2d.transform(at);
				if (tanknr%2==0) {
					if(barrel[tanknr].getMaxX()<x){
						g2d.drawImage(img_bullet, x, y, this);
						flashing=tanknr;
					}
					if(barrel[tanknr].getMaxX()+15<x){
						flashing=-1;
					}
				}
				else {
					if(barrel[tanknr].getMinX()>x){
						g2d.drawImage(img_bullet, x, y, this);
						flashing=tanknr;
					}
					if(barrel[tanknr].getMinX()-15>x){
						flashing=-1;
					}
				}
				bullet.setLocation(x, y);
				bullet.setSize(5, 5);
				//g2d.draw(bullet);
				g2d.setTransform(orig);
				
				for(int i = 0; i !=10; i++) {
					if(shot_Counter+i<mydata.size()){
						if(mydata.get(shot_Counter+i).hit){
							shoting = -1;
							shot_Counter=0;
							Point myexp = new Point(x,y);
							expl = myexp;
							expl_state = 0;
						}
					}
				}
				
				shot_Counter+=10;
			}
			else {
				shoting = -1;
				shot_Counter=0;
			}
		}
	}
	/**
	 * <h1>draw the label</h1>
	 * <br>
	 * draws the label with stats<br>
	 * 
	 * @param g2d Graphics to show the objects
	 * @param tanknr int which tank
	 */
	private void drawLabel(Graphics2D g2d, int tanknr) {

        // x = posx - (width)
        int width = (int) mytanks[tanknr].get_tankdata().width;
        int posx = (int) mytanks[tanknr].get_tankdata().posx;
        int x;
        
        Color mycolor = g2d.getColor();
        if (tanknr%2==0) {
			x = 100 + (40 * tanknr) - width;
		}
        else {
			x = (int) (test.width - ( 100 + (40 * tanknr)- width));
        }
		int y=50;
		if(test.player!=tanknr){
        	g2d.setColor(Color.white);
        }
		else{
			 g2d.setColor(Color.yellow);
		}
		int offset=(int)(test.height/3);//230;
		String mytext = null;
		if(mytanks[tanknr].is_dead()){
	        g2d.setColor(mycolor);
		}
        // 10+offset
        y = (int) (10+offset);
        mytext = new String("Power: "+mytanks[tanknr].get_tankdata().power);
        g2d.drawString(mytext, x, y);
        // 10+offset
        offset+=20;
        y = (int) (10+offset);
        mytext = new String("Angle: "+mytanks[tanknr].get_tankdata().angle);
        g2d.drawString(mytext, x, y);
        // 10+offset
        offset+=20;
        g2d.setColor(mycolor);
        y = (int) (10+offset);
        mytext = new String("posx: "+mytanks[tanknr].get_tankdata().posx);
        g2d.drawString(mytext, x, y);
        // 10+offset
        offset+=20;
        y = (int) (10+offset);
        mytext = new String("posy: "+mytanks[tanknr].get_tankdata().posy);
        g2d.drawString(mytext, x, y);
        // 10+offset
        offset+=20;
	    if(!mytanks[tanknr].is_dead()){
		    g2d.setColor(Color.green);
		}
        y = (int) (10+offset);
        mytext = new String("score: "+mytanks[tanknr].get_score());
        g2d.drawString(mytext, x, y);
        // 10+offset
        offset+=20;
        g2d.setColor(Color.red);
        y = (int) (10+offset);
        if(mytanks[tanknr].is_dead()){
        	mytext = new String("!!! FOOBAR !!!");
        }
        else{
        	mytext = new String("hit: "+mytanks[tanknr].get_hit());
        }
        g2d.drawString(mytext, x, y);
        if(test.player!=tanknr){
        	g2d.setColor(Color.white);
        }
		else{
			 g2d.setColor(Color.yellow);
		}
        if(mytanks[tanknr].is_dead()){
	        g2d.setColor(mycolor);
		}
        if (tanknr%2==0) {
			x = posx - (width);
		}
        else {
			x = posx - width;
        }
        // 150+offset
        offset+=100;
        y = (int) (10+offset);
        g2d.setFont(new Font("Purisa", Font.BOLD, 25));
        g2d.drawString(""+(tanknr+1), x, y);
        g2d.setColor(mycolor);
        g2d.setFont(new Font("Purisa", Font.BOLD, 12));
	}

	/**
	 * <h1>draw the tank</h1>
	 * <br>
	 * draws the tank at the right position<br>
	 * 
	 * @param g2d Graphics to show the objects
	 * @param tanknr int which tank
	 */
	private void drawTank(Graphics2D g2d, int tanknr) {
        
        // x = posx - (width)
        int width = (int) mytanks[tanknr].get_tankdata().width;
        int posx = (int) mytanks[tanknr].get_tankdata().posx;
        int x = posx - (width);
        
        // y = maxy - posy - height
        int height = (int) mytanks[tanknr].get_tankdata().height;
        int posy = (int) mytanks[tanknr].get_tankdata().posy;
        int y = (int) (this.getHeight() - posy - height);

        tank[tanknr].setLocation(x,y);
        tank[tanknr].setSize(width,height);
        
        drawBarrel(g2d,tanknr);
        g2d.drawImage(img_tank[tanknr], x, y, this);
		//g2d.draw(tank[tanknr]);
	}
	/**
	 * <h1>draw the barrel</h1>
	 * <br>
	 * draws the barrel of the tank in the correct angle<br>
	 * 
	 * @param g2d Graphics to show the objects
	 * @param tanknr int which tank
	 */
	private void drawBarrel(Graphics2D g2d, int tanknr) {
		
		AffineTransform orig = g2d.getTransform();
		
		AffineTransform at;
		
		double angle = mytanks[tanknr].get_tankdata().angle;
		
        int x = 0;
        int y = 0;
        
		if(tanknr%2==0)
		{
			// p 0&2
			x = (int)(mytanks[tanknr].get_tankdata().posx - mytanks[tanknr].get_tankdata().width / 2);
			y = (int)(this.getHeight() - mytanks[tanknr].get_tankdata().posy - mytanks[tanknr].get_tankdata().height);
			barrel[tanknr].setLocation(x,y);
			barrel[tanknr].setSize((int) test.barrel_width, (int) test.barrel_height);
			at= AffineTransform.getRotateInstance(Math.toRadians(-angle),barrel[tanknr].x, barrel[tanknr].y+barrel[tanknr].height);
		}
		else
		{
			// p 1&3
			x = (int) (mytanks[tanknr].get_tankdata().posx - mytanks[tanknr].get_tankdata().width / 2 - test.barrel_width);
			y = (int)(this.getHeight() - mytanks[tanknr].get_tankdata().posy - mytanks[tanknr].get_tankdata().height);
			barrel[tanknr].setLocation(x,y);
			barrel[tanknr].setSize((int) test.barrel_width, (int) test.barrel_height);
			at= AffineTransform.getRotateInstance(Math.toRadians(angle),barrel[tanknr].x+barrel[tanknr].width, barrel[tanknr].y);
		}
		at.createTransformedShape(barrel[tanknr]);
		g2d.transform(at);
		g2d.drawImage(img_barrel[tanknr], x, y, this);
		//g2d.draw(barrel[tanknr]);
		if(tanknr==flashing){
			if(tanknr%2==0)
			{
				g2d.drawImage(img_muzfl, x+(int) test.barrel_width, y, this);
			}
			else 
			{
				at= AffineTransform.getRotateInstance(Math.toRadians(180),barrel[tanknr].x+barrel[tanknr].width, barrel[tanknr].y);
				g2d.transform(at);
				g2d.drawImage(img_muzfl, x+(int) test.barrel_width+(int) test.barrel_width, y-(int) test.barrel_height, this);
			}
		}
		g2d.setTransform(orig);
	}
	/**
	 * <h1>constructor</h1>
	 * <br>
	 * sets the background color<br>
	 * initialize the arrays<br>
	 * create elements of the arrays<br>
	 * 
	 * @param tanks holds all tanks which are involved in the game
	 */
	public draw_game(tank[] tanks) {
		Date mydate = new Date(); // date
		long timestamp = mydate.getTime(); // ms
		timestamp/=1000; // s
		timestamp/=60; // min
		timestamp/=60; // h
		
		act_player = 0;
		
		Random myrandom = new Random();
		amount_tree = 5+Math.abs(myrandom.nextInt()%20);
		amount_buildings = 1+Math.abs(myrandom.nextInt()%7);
		amount_clouds = 6+Math.abs(myrandom.nextInt()%25);
		amount_stars = 600+Math.abs(myrandom.nextInt()%600);
		
		setSize((int) test.width, (int) test.width);
		
		mytanks=tanks;
		
		// 5 - 9 morning
		if(timestamp%24>=5 && timestamp%24<10){
			mybgc = new Color(14, 131, 205);
			mybgc1 = new Color(0, 0, 0);
			daytime = 0;
		}
		// 10 - 16 day
		else if(timestamp%24>=10 && timestamp%24<17){
			mybgc = new Color(14, 131, 205);
			mybgc1 = new Color(0, 0, 0);
			daytime = 1;
		}
		// 17 - 20 evening
		else if(timestamp%24>=17 && timestamp%24<21){
			mybgc = new Color(8, 12, 68);
			mybgc1 = new Color(0, 0, 0);
			daytime = 2;
		}
		// 21 - 4 night
		else{
			mybgc = new Color(8, 12, 68);
			mybgc1 = new Color(0, 0, 0);
			daytime = 3;
		}
		mygdc = new Color(154, 57, 5);
		myghc = new Color(16, 44, 18);
		this.setBackground(mybgc);
		
		bullet = new Rectangle();
		bunker = new Rectangle();
		ground_d = new Rectangle();
		ground_h = new Rectangle();
		tree = new Rectangle[amount_tree];
		house = new Rectangle[amount_buildings];
		tank = new Rectangle[tanks.length];
		barrel = new Rectangle[tanks.length];
		cloud = new Rectangle[amount_clouds];
		myimg = new ImageIcon[tanks.length];
		img_tank = new Image[tanks.length];
		myimg1 = new ImageIcon[tanks.length];
		img_barrel = new Image[tanks.length];
		myimg2 = new ImageIcon(this.getClass().getResource("/bullet.png"));
		img_bullet = myimg2.getImage();
		myimg3 = new ImageIcon(this.getClass().getResource("/muzzleflash.png"));
		img_muzfl = myimg3.getImage();
		myimg4 = new ImageIcon(this.getClass().getResource("/explosion_sprite.png"));
		img_explosions = myimg4.getImage();
		myimg5 = new ImageIcon(this.getClass().getResource("/bunker.png"));
		img_bunker = myimg5.getImage();
		myimg6 = new ImageIcon[2];
		img_tree = new Image[amount_tree];
		tree_x = new int[amount_tree];
		myimg7 = new ImageIcon[2];
		myimg8 = new ImageIcon[2];
		img_house = new Image[amount_buildings];
		img_top = new Image[amount_buildings];
		house_x = new int[amount_buildings];
		house_elements = new int[amount_buildings];
		myimg9 = new ImageIcon[2];
		img_cloud = new Image[amount_clouds];
		cloud_y = new int[amount_clouds];
		cloud_x = new int[amount_clouds];
		
		// tanks
		for(int i = 0; i < tank.length; i++) {
			tank[i] = new Rectangle();
			barrel[i] = new Rectangle();
			if (i%2==0) {
		    	myimg[i] = new ImageIcon(this.getClass().getResource("/tank_0.png"));
		    	myimg1[i] = new ImageIcon(this.getClass().getResource("/barrel_0.png"));
		    }
		    else {
		    	myimg[i] = new ImageIcon(this.getClass().getResource("/tank_1.png"));
		    	myimg1[i] = new ImageIcon(this.getClass().getResource("/barrel_1.png"));
		    }
		    img_tank[i] = myimg[i].getImage();
		    img_barrel[i] = myimg1[i].getImage();
		}
		
		// buildings
		myimg7[0] = new ImageIcon(this.getClass().getResource("/house_0.png"));
		myimg7[1] = new ImageIcon(this.getClass().getResource("/house_1.png"));
		myimg8[0] = new ImageIcon(this.getClass().getResource("/top_0.png"));
		myimg8[1] = new ImageIcon(this.getClass().getResource("/top_1.png"));
		for(int i = 0; i < amount_buildings; i++){
			house[i] = new Rectangle();
			house_x[i] = myrandom.nextInt()%(int)test.width;
			house_elements[i] = myrandom.nextInt()%(int)3;
			if(house_x[i] < 0){
				house_x[i]*=-1;
			}
			if(house_elements[i] < 0){
				house_elements[i]*=-1;
			}
			house_elements[i]++;
			int width, height;
			width = myimg7[0].getIconWidth();
			height = house_elements[i] * myimg7[0].getIconHeight() + myimg8[0].getIconHeight();
			house[i].setSize(width, height);
			ImageIcon tmp = new ImageIcon();
			ImageIcon tmp1 = new ImageIcon();
			if(myrandom.nextInt()%2==0){
				tmp = myimg7[0];
				tmp.setImage(myimg7[0].getImage()); 
				tmp1 = myimg8[0];
				tmp1.setImage(myimg8[0].getImage()); 
			}
			else{
				tmp = myimg7[1];
				tmp.setImage(myimg7[1].getImage());
				tmp1 = myimg8[1];
				tmp1.setImage(myimg8[1].getImage());
			}
			img_house[i] = tmp.getImage();
			img_top[i] = tmp1.getImage();
		}
		// trees
		myimg6[0] = new ImageIcon(this.getClass().getResource("/tree_d.png"));
		myimg6[1] = new ImageIcon(this.getClass().getResource("/tree_p.png"));
		for(int i = 0; i < amount_tree; i++){
			tree[i] = new Rectangle();
			tree_x[i] = myrandom.nextInt()%(int)test.width;
			if(tree_x[i] < 0){
				tree_x[i]*=-1;
			}
			int width, height;
			double scale = myrandom.nextInt()%35;
			if(scale < 0.0){
				scale*=-1;
			}
			width = 1 + (int) (( (double)myimg6[0].getIconWidth() * (90+scale)) / 100.0 );
			height = 1 + (int) (( (double)myimg6[0].getIconHeight() * (90+scale)) / 100.0 );
			tree[i].setSize(width, height);
			ImageIcon tmp = new ImageIcon();
			if(myrandom.nextInt()%2==0){
				tmp = myimg6[0];
				tmp.setImage(myimg6[0].getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT)); 
			}
			else{
				tmp = myimg6[1];
				tmp.setImage(myimg6[1].getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
			}
		    img_tree[i] = tmp.getImage();
		}
		// clouds
		myimg9[0] = new ImageIcon(this.getClass().getResource("/cloud_0.png"));
		myimg9[1] = new ImageIcon(this.getClass().getResource("/cloud_1.png"));
		for(int i = 0; i < amount_clouds; i++){
			cloud[i] = new Rectangle();
			cloud_y[i] = myrandom.nextInt()%((int)test.height/4);
			if(cloud_y[i] < 0){
				cloud_y[i]*=-1;
			}
			cloud_x[i] = myrandom.nextInt()%((int)test.width*2);
			if(cloud_x[i] > 0){
				cloud_x[i]*=-1;
			}
			int width, height;
			double scale = myrandom.nextInt()%35;
			if(scale < 0.0){
				scale*=-1;
			}
			width = 1 + (int) (( (double)myimg9[0].getIconWidth() * (95+scale)) / 100.0 );
			height = 1 + (int) (( (double)myimg9[0].getIconHeight() * (95+scale)) / 100.0 );
			cloud[i].setSize(width, height);
			ImageIcon tmp = new ImageIcon();
			if(myrandom.nextInt()%2==0){
				tmp = myimg9[0];
				tmp.setImage(myimg9[0].getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT)); 
			}
			else{
				tmp = myimg9[1];
				tmp.setImage(myimg9[1].getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT));
			}
		    img_cloud[i] = tmp.getImage();
		}
		
	}
	/**
	 * <h1>is shooting</h1>
	 * <br>
	 * check if a shot is drawing<br>
	 * 
	 * @return boolean true if shooting false if not
	 */
	public boolean isShooting() {
		if(shoting==-1) {
			return false;
		}
		else {
			return true;
		}
	}
	/**
	 * <h1>set player</h1>
	 * <br>
	 * set the actual gaming player<br>
	 * 
	 * @param player
	 */
	public void set_player(int player) {
		act_player = player;
	}
}
