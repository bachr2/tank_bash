package tank_bash;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import view.draw_game;
import calculation.hit;
import calculation.shot;
import calculation.tank;

/**
 * 
 * @author ricu
 *
 */
@SuppressWarnings("serial")
public class test extends JFrame {
	/**
	 * break variable
	 */
	private static boolean gamebreak = false;
	/**
	 * win variable
	 */
	private static boolean win = false;
	/**
	 * actual player
	 */
	public static int player = 0;
	/**
	 * amount of players
	 */
	public static int anz_player = 0;
	/**
	 * tank width
	 */
	public static double tank_width = 50;
	/**
	 * tank height
	 */
	public static double tank_height = 19;
	/**
	 * barrel width
	 */
	public static double barrel_width = 37;
	/**
	 * barrel height
	 */
	public static double barrel_height = 7;
	/**
	 * actual window width
	 */
	public static double width = 906;
	/**
	 * actual window height
	 */
	public static double height = 730;
	/**
	 * start window width
	 */
	public static double orig_width = 906;
	/**
	 * start window height
	 */
	public static double orig_height = 730;
	/**
	 * window width difference
	 */
	public static double diff_width = 6;
	/**
	 * window height difference
	 */
	public static double diff_height = 30;
	/**
	 * ground1 width
	 */
	public static double ground1_width = 906;
	/**
	 * ground1 height
	 */
	public static double ground1_height = 5;
	/**
	 * ground2 width
	 */
	public static double ground2_width = 906;
	/**
	 * ground2 height
	 */
	public static double ground2_height = 10;
	/**
	 * bunker width
	 */
	public static double bunker_width = 200;
	/**
	 * bunker height
	 */
	public static double bunker_height = 90;
	/**
	 * game tanks
	 */
	private static tank[] mytanks;
	/**
	 * game hit
	 */
	private static hit myhit;
	/**
	 * game
	 */
	private static draw_game mygame = null;
	/**
	 * menu bar
	 */
	private static JMenuBar mybar = null;
	/**
	 * menu
	 */
	private static JMenu mymenu = null;
	/**
	 * menu item
	 */
	private static JMenuItem item_con = null;
	/**
	 * menu item
	 */
	private static JMenuItem item_pau = null;
	/**
	 * menu item
	 */
	private static JMenuItem item_res = null;
	/**
	 * menu item
	 */
	private static JMenuItem item_exit = null;
	/**
	 * <h1>constructor</h1>
	 * <br>
	 * Standard constructor<br>
	 * Initialize the settings, calculation and UI<br>
     * 
     */
	public test() {
		
		initSettings();
		initCalc();
        initUI();
        
    }
	/**
	 * <h1>initialize settings</h1>
	 * <br>
	 * Initialize the game settings<br>
	 * Settings:<br>
	 * <ul>
	 * <li>Player</li>
	 * </ul>
	 * 
	 */
	private void initSettings() {
		
		Object[] options = {"two player","three player","four player"};
		int n = JOptionPane.showOptionDialog(this,"Player?","Player Menu",
				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
		switch(n){
		case 0:
			anz_player = 2;
			break;
		case 1:
			anz_player = 3;
			break;
		case 2:
			anz_player = 4;
			break;
		default:
			System.exit(PROPERTIES);
			break;
		}
		
	}
	/**
	 * <h1>initialize UI</h1>
	 * <br>
	 * Initialize the game UI<br>
	 * 
	 */
	private void initUI() {
        
		mygame = new draw_game(mytanks);
		mybar = new JMenuBar();
		mymenu = new JMenu("Menu");
		item_pau = new JMenuItem("pause");
		item_con = new JMenuItem("continue");
		item_res = new JMenuItem("reset");
		item_exit = new JMenuItem("exit");
		
		mymenu.add(item_pau);
		mymenu.add(item_con);
		mymenu.add(item_res);
		mymenu.add(item_exit);
		mybar.add(mymenu);
		mybar.setEnabled(false);
		
		add(mygame);
		setJMenuBar(mybar);

        setSize((int)width,(int)height);
        
        setResizable(false);

        setTitle("Tank Bash - Player "+player);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        item_pau.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gamebreak=!gamebreak;
            }
         });
        item_con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	gamebreak=!gamebreak;
            }
         });
        item_res.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	StringBuilder cmd = new StringBuilder();
                cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
                for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
                    cmd.append(jvmArg + " ");
                }
                cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
                cmd.append(test.class.getName()).append(" ");
                /*for (String arg : args) {
                    cmd.append(arg).append(" ");
                }*/
                try {
					Runtime.getRuntime().exec(cmd.toString());
	                System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
	                System.exit(ERROR);
				}
            }
         });
        item_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
    			System.exit(0);
            }
         });
        
        this.addKeyListener(new KeyListener(){
		@Override
		public void keyPressed(KeyEvent key) {
			if (!mygame.isShooting()&&!gamebreak) {
				switch (key.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					mytanks[player].power_up();
					break;
				case KeyEvent.VK_UP:
					mytanks[player].barrel_up();
					break;
				case KeyEvent.VK_DOWN:
					mytanks[player].barrel_down();
					break;
				case KeyEvent.VK_LEFT:
					mytanks[player].go_left();
					break;
				case KeyEvent.VK_RIGHT:
					mytanks[player].go_right();
					break;
				case KeyEvent.VK_PAUSE:
					gamebreak = !gamebreak;
					break;
				}
			}
			else{
				if(key.getKeyCode()==KeyEvent.VK_PAUSE){
					gamebreak = !gamebreak;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent key) {
			if(key.getKeyCode()==KeyEvent.VK_SPACE && !mygame.isShooting() && !gamebreak) {
				// fire
				mytanks[player].shot();
				shot myshot = mytanks[player].get_shotdata();
				mygame.shot(myshot, player);
				int score = myhit.calc_hit(myshot,player);
				if(score>0&&score<=anz_player) {
					if(mytanks[player].get_tankdata().team!=mytanks[score-1].get_tankdata().team){
						mytanks[player].add_score(1);
					}
					mytanks[score-1].tank_hit();
				}
				int team1 = 0;
				int team2 = 0;
				for(int i = 0; i < anz_player; i++){
					if(!mytanks[i].is_dead()){
						if(mytanks[i].get_tankdata().team==0){
							team1++;
						}
						else{
							team2++;
						}
					}
				}
				if (team1 == 0) {
					win = true;
					mygame.winner(1);
				}
				if (team2 == 0) {
					win = true;
					mygame.winner(0);
				}
				player++;
				if(player>=anz_player){
					player=0;
				}
				if(!win){
					mytanks[player].reset();
					while(mytanks[player].is_dead()){
						player++;
						if(player>=anz_player){
							player=0;
						}
						mytanks[player].reset();
					}
				}
			}
		}

		@Override
		public void keyTyped(KeyEvent key) {
		}
	});
	Timer rf = new Timer(){};
	rf.scheduleAtFixedRate(new TimerTask(){
		boolean first = true;
		@Override
		public void run() {
			width = getContentPane().getWidth();
			height = getContentPane().getHeight();
			diff_width = orig_width - width;
			diff_height = orig_height - height;
			ground1_width = width;
			ground2_width = width;
			for(int i = 0; i < anz_player; i++){
				mytanks[i].refresh_min_max();
			}
			if (!gamebreak) {
				setTitle("Tank Bash - Player " + player + " - RUN");
				item_pau.setVisible(true);
				item_con.setVisible(false);
				mygame.set_player(player);
				mygame.repaint();
				first=true;
			}
			else {
				if(first){
					setTitle("Tank Bash - Player " + player + " - PAUSED");
					item_pau.setVisible(false);
					item_con.setVisible(true);
					first=false;
				}
			}
		}}, 0, 50);
        
    } 
	/**
	 * <h1>initialize calculation</h1>
	 * <br>
	 * Initialize the game UI<br>
	 * 
	 */
	private void initCalc() {
		mytanks = new tank[anz_player];
		
		if(anz_player==2) {
			mytanks[0] = new tank(tank_width,tank_height,0,0);
			mytanks[0].set_pos(100, ground1_height+ground2_height);
			mytanks[1] = new tank(tank_width,tank_height,1,1);
			mytanks[1].set_pos(width-(100+diff_width)+tank_width, ground1_height+ground2_height);
		}
		else if(anz_player==3) {
			mytanks[0] = new tank(tank_width,tank_height,0,0);
			mytanks[0].set_pos(100, ground1_height+ground2_height);
			mytanks[1] = new tank(tank_width,tank_height,1,1);
			mytanks[1].set_pos(width-(100+diff_width)+tank_width, ground1_height+ground2_height);
			mytanks[2] = new tank(tank_width,tank_height,0,2);
			mytanks[2].set_pos(100+80, ground1_height+ground2_height);
		}
		else {
			mytanks[0] = new tank(tank_width,tank_height,0,0);
			mytanks[0].set_pos(100, ground1_height+ground2_height);
			mytanks[1] = new tank(tank_width,tank_height,1,1);
			mytanks[1].set_pos(width-(100+diff_width)+tank_width, ground1_height+ground2_height);
			mytanks[2] = new tank(tank_width,tank_height,0,2);
			mytanks[2].set_pos(100+80, ground1_height+ground2_height);
			mytanks[3] = new tank(tank_width,tank_height,1,3);
			mytanks[3].set_pos(width-(100+80+diff_width)+tank_width, ground1_height+ground2_height);
		}
		
		myhit = new hit();
		for(int i = 0; i < mytanks.length; i++){
			myhit.add_tank(mytanks[i]);
		}
    } 
	/**
	 * <h1>My main function</h1>
	 * @param args
	 */
	public static void main(String[] args) {
		
		width = orig_width = Toolkit.getDefaultToolkit().getScreenSize().width/6*5;
		height = orig_height = Toolkit.getDefaultToolkit().getScreenSize().height/6*5;
		ground1_width = width;
		ground2_width = width;
		
		test ex = new test();
        ex.setVisible(true);
        
	}

}
