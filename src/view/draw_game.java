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
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Date;

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
	private tank[] mytanks;
	
	private int shoting = -1;
	private shot theshot = null;
	private int shot_Counter = 0;
	private int flashing = -1;
	
	private Color mybgc = null;
	private Color mybgc1 = null;
	private Color mygdc = null;
	private Color myghc = null;
	
	private Rectangle bullet = null;
	private Rectangle[] tank = null;
	private Rectangle[] barrel = null;
	
	private Rectangle ground_d = null;
	private Rectangle ground_h = null;
	
	private ImageIcon myimg[] = null;
    private Image img_tank[] = null;
    private ImageIcon myimg1[] = null;
    private Image img_barrel[] = null;
    private ImageIcon myimg2 = null;
    private Image img_bullet = null;
    private ImageIcon myimg3 = null;
    private Image img_muzfl = null;
    private ImageIcon myimg4 = null;
    private Image img_explotion = null;
    
    private String daytime = null;
    
    private Point expl = null;
    private int expl_state = -1;
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
		drawDesignStatic(g2d);
		drawDesignDynamic(g2d);
		for(int i = 0; i < mytanks.length; i++) {
			drawShot(g2d,i);
			drawLabel(g2d,i);
			drawTank(g2d,i);
		}
		drawExplosion(g2d);
    }
	/**
	 * <h1>fire a shot</h1>
	 * <br>
	 * allow the draw process of the shot<br>
	 * saves the data of the shot<br>
	 * 
	 * @param shot the shot to draw
	 * @param tanknr int which tank
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
	 * @param g Graphics to show the objects
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
				g2d.drawImage(img_explotion, expl.x-20, expl.y+20, expl.x+20, expl.y-20, expl_state*40, 0, expl_state*40+40, 40, this);
				expl_state++;
			}
		}
	}
	/**
	 * <h1>draw the design</h1>
	 * <br>
	 * draws the dynamic part of the world design<br>
	 * 
	 * @param g Graphics to show the objects
	 */
	private void drawDesignDynamic(Graphics2D g2d) {
	}
	/**
	 * <h1>draw the design</h1>
	 * <br>
	 * draws the static part of the world design<br>
	 * 
	 * @param g Graphics to show the objects
	 */
	private void drawDesignStatic(Graphics2D g2d) {
		
		Color mycolor = g2d.getColor();
		g2d.setFont(new Font("Purisa", Font.BOLD, 12));
		g2d.setColor(mygdc);
		ground_d.setBounds(0, (int) (test.height-10), (int) test.width, 10);
		g2d.fill(ground_d);
		
		g2d.setColor(myghc);
		ground_h.setBounds(0, (int) (test.height-20), (int) test.width, 10);
		g2d.fill(ground_h);
		
		GradientPaint gp = new GradientPaint(0, 0, mybgc1, 0, 100, mybgc);
        g2d.setPaint(gp);
		g2d.drawRect(0, 0, (int) test.width, 100);
		g2d.fillRect(0, 0, (int) test.width, 100);
		
		g2d.setColor(mycolor);
		
		/*for(int i = 0; i < test.width; i+=10) {
			for(int j = 0; j < test.height; j+=10) {
				g2d.drawRect(i, j, 10, 10);
			}
		}*/
	}
	/**
	 * <h1>draw the shot</h1>
	 * <br>
	 * draws the shot which was fired<br>
	 * 
	 * @param g Graphics to show the objects
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
	 * @param g Graphics to show the objects
	 * @param tanknr int which tank
	 */
	private void drawLabel(Graphics2D g2d, int tanknr) {

        // x = posx - (width)
        int width = (int) mytanks[tanknr].get_tankdata().width;
        int posx = (int) mytanks[tanknr].get_tankdata().posx;
        int x;
        
        Color mycolor = g2d.getColor();
        if (tanknr%2==0) {
			x = posx - (width);
		}
        else {
			x = posx - (2*width);
        }
        g2d.setColor(Color.yellow);
		int y=50;
		g2d.drawString(daytime, (int) (test.width/2), y);
		if(test.player!=tanknr){
        	g2d.setColor(Color.white);
        }
		else{
			 g2d.setColor(Color.yellow);
		}
        // 10+offset
        int offset=80;
        y = (int) (10+offset);
        String mytext = new String("Power: "+mytanks[tanknr].get_tankdata().power);
        g2d.drawString(mytext, x, y);
        // 10+offset
        offset+=20;
        y = (int) (10+offset);
        mytext = new String("Angle: "+mytanks[tanknr].get_tankdata().angle);
        g2d.drawString(mytext, x, y);
        /*// 10+offset
        offset+=20;
        y = (int) (10+offset);
        mytext = new String("posx: "+mytanks[tanknr].get_tankdata().posx);
        g2d.drawString(mytext, x, y);
        // 10+offset
        offset+=20;
        y = (int) (10+offset);
        mytext = new String("posy: "+mytanks[tanknr].get_tankdata().posy);
        g2d.drawString(mytext, x, y);*/
        g2d.setColor(Color.green);
        // 10+offset
        offset+=20;
        y = (int) (10+offset);
        mytext = new String("score: "+mytanks[tanknr].get_score());
        g2d.drawString(mytext, x, y);
        g2d.setColor(Color.red);
        // 10+offset
        offset+=20;
        y = (int) (10+offset);
        if(mytanks[tanknr].is_dead()){
        	mytext = new String("!!! FOOBAR !!!");
        }
        else{
        	mytext = new String("hit: "+mytanks[tanknr].get_hit());
        }
        g2d.drawString(mytext, x, y);
        g2d.setColor(mycolor);
	}

	/**
	 * <h1>draw the tank</h1>
	 * <br>
	 * draws the tank at the right position<br>
	 * 
	 * @param g Graphics to show the objects
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
	 * @param g Graphics to show the objects
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
	 * @param tank[] holds all tanks which are involved in the game
	 */
	public draw_game(tank[] tanks) {
		Date mydate = new Date(); // date
		long timestamp = mydate.getTime(); // ms
		timestamp/=1000; // s
		timestamp/=60; // min
		timestamp/=60; // h
		
		setSize((int) test.width, (int) test.width);
		
		mytanks=tanks;
		
		// 5 - 9
		if(timestamp%24>=5 && timestamp%24<10){
			daytime = new String("morning");
			mybgc = new Color(14, 131, 205);
			mybgc1 = new Color(0, 0, 0);
		}
		// 10 - 16
		else if(timestamp%24>=10 && timestamp%24<17){
			daytime = new String("day");
			mybgc = new Color(14, 131, 205);
			mybgc1 = new Color(0, 0, 0);
		}
		// 17 - 20
		else if(timestamp%24>=17 && timestamp%24<21){
			daytime = new String("evening");
			mybgc = new Color(8, 12, 68);
			mybgc1 = new Color(0, 0, 0);
		}
		// 21 - 4
		else{
			daytime = new String("night");
			mybgc = new Color(8, 12, 68);
			mybgc1 = new Color(0, 0, 0);
		}
		mygdc = new Color(154, 57, 5);
		myghc = new Color(16, 44, 18);
		this.setBackground(mybgc);
		
		bullet = new Rectangle();
		ground_d = new Rectangle();
		ground_h = new Rectangle();
		tank = new Rectangle[tanks.length];
		barrel = new Rectangle[tanks.length];
		myimg = new ImageIcon[tanks.length];
		img_tank = new Image[tanks.length];
		myimg1 = new ImageIcon[tanks.length];
		img_barrel = new Image[tanks.length];
		myimg2 = new ImageIcon("bullet.png");
		img_bullet = myimg2.getImage();
		myimg3 = new ImageIcon("muzzleflash.png");
		img_muzfl = myimg3.getImage();
		myimg4 = new ImageIcon("explosion_sprite.png");
		img_explotion = myimg4.getImage();
		
		for(int i = 0; i < tank.length; i++) {
			tank[i] = new Rectangle();
			barrel[i] = new Rectangle();
			if (i%2==0) {
		    	myimg[i] = new ImageIcon("tank_0.png");
		    	myimg1[i] = new ImageIcon("barrel_0.png");
		    }
		    else {
		    	myimg[i] = new ImageIcon("tank_1.png");
		    	myimg1[i] = new ImageIcon("barrel_1.png");
		    }
		    img_tank[i] = myimg[i].getImage();
		    img_barrel[i] = myimg1[i].getImage();
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
}
