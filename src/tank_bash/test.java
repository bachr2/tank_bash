package tank_bash;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

@SuppressWarnings("serial")
public class test extends JFrame {
	
	private static boolean gamebreak = false;
	
	public static int player = 0;
	public static int anz_player = 0;
	public static double tank_width = 50;
	public static double tank_height = 19;
	public static double barrel_width = 37;
	public static double barrel_height = 7;
	public static double width = 906;
	public static double height = 730;
	public static double orig_width = 906;
	public static double orig_height = 730;
	public static double diff_width = 6;
	public static double diff_height = 30;
	
	static tank[] mytanks;
	static hit myhit;
	
	public test() {
		
		initSettings();
		initCalc();
        initUI();
        
    }
	private void initSettings() {
		
		Object[] options = {"two player","three player","four player"};
		int n = JOptionPane.showOptionDialog(this,"Player?","Player Menu",
				JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[2]);
		switch(n){
		case 1:
			anz_player = 3;
			break;
		case 2:
			anz_player = 4;
			break;
		default:
			anz_player = 2;
			break;
		}
		
	}
	private void initUI() {
        
		final draw_game mygame = new draw_game(mytanks);
		final JMenuBar mybar = new JMenuBar();
		final JMenu mymenu = new JMenu("Menu");
		final JMenuItem item_con = new JMenuItem("continue");
		final JMenuItem item_res = new JMenuItem("reset");
		final JMenuItem item_exit = new JMenuItem("exit");
		
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
				case KeyEvent.VK_ESCAPE:
					gamebreak = !gamebreak;
					break;
				}
			}
			else{
				if(key.getKeyCode()==KeyEvent.VK_ESCAPE){
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
					mytanks[player].add_score(1);
					mytanks[score-1].tank_hit();
				}
				player++;
				if(player>=anz_player){
					player=0;
				}
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
			if (!gamebreak) {
				setTitle("Tank Bash - Player " + player + " - RUN");
				mygame.repaint();
				first=true;
			}
			else {
				if(first){
					setTitle("Tank Bash - Player " + player + " - PAUSED");
					mygame.repaint();
					first=false;
				}
			}
		}}, 0, 50);
        
    } 
	
	private void initCalc() {
		mytanks = new tank[anz_player];
		
		if(anz_player==2) {
			mytanks[0] = new tank(tank_width,tank_height,0,0);
			mytanks[0].set_pos(100, 20);
			mytanks[1] = new tank(tank_width,tank_height,1,1);
			mytanks[1].set_pos(width-(100+diff_width)+tank_width, 20);
		}
		else if(anz_player==3) {
			mytanks[0] = new tank(tank_width,tank_height,0,0);
			mytanks[0].set_pos(100, 20);
			mytanks[1] = new tank(tank_width,tank_height,1,1);
			mytanks[1].set_pos(width-(100+diff_width)+tank_width, 20);
			mytanks[2] = new tank(tank_width,tank_height,0,2);
			mytanks[2].set_pos(100+80, 20);
		}
		else {
			mytanks[0] = new tank(tank_width,tank_height,0,0);
			mytanks[0].set_pos(100, 20);
			mytanks[1] = new tank(tank_width,tank_height,1,1);
			mytanks[1].set_pos(width-(100+diff_width)+tank_width, 20);
			mytanks[2] = new tank(tank_width,tank_height,0,2);
			mytanks[2].set_pos(100+80, 20);
			mytanks[3] = new tank(tank_width,tank_height,1,3);
			mytanks[3].set_pos(width-(100+80+diff_width)+tank_width, 20);
		}
		
		myhit = new hit();
		for(int i = 0; i < mytanks.length; i++){
			myhit.add_tank(mytanks[i]);
		}
    } 

	public static void main(String[] args) {
		
		width = orig_width = Toolkit.getDefaultToolkit().getScreenSize().width/6*5;
		height = orig_height = Toolkit.getDefaultToolkit().getScreenSize().height/6*5;
		
		test ex = new test();
        ex.setVisible(true);
        
	}

}
