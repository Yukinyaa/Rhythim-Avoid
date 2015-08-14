package tk.yukinyaa.RAv;

import java.awt.*;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Character extends Coldomain{
	int x,y;
	static int frameSensivity = 1000;
	static final int width=30;

	public Character(String imgurl){
		super(Board.B_WIDTH/2-width, Board.B_HEIGHT-width, Board.B_WIDTH/2, Board.B_HEIGHT);
		ison = true;
		x=Board.B_WIDTH/2;
		y=Board.B_HEIGHT;
		
	}
	static void rm(int i)
	{
		rm((Coldomain)list.get(i));
		list.remove(i);
	}
	public void rm()
	{
		rm(this);
	}
	int vx,vy;
	int lframe;
	public void render(Graphics g, int frame)
	{

		//vx*=0.2f;
		//vy*=0.2f;
		x+=vx;//*(frame-lframe)/frameSensivity;
		y+=vy;//*(frame-lframe)/frameSensivity;
		if(x>750)
			x=750;
		else if(x<0)
			x=0;
		x1 = x-width;y1 = y-width;
		x2 = x;y2 = y;
		g.fillRect(x,y,-width,-width);
		lframe = frame;
	}
	public boolean check()
	{
		return chk((Coldomain)this);
	}
	//int lframe2 = 0;
	public void acc()
	{
		final int SPEED = 15;
		vx=(key_left?-10:0)+(key_right?10:0);
		//vx=x;//*(frame-lframe2)/frameSensivity;
		//vy=y;//*(frame-lframe2)/frameSensivity;
		//lframe2 = frame;
		//pr
	}
	boolean key_left,key_right;
	public void keyPressed(KeyEvent e,int frame) {

        int key = e.getKeyCode();
        //System.out.println(key);
        if (key == KeyEvent.VK_LEFT) {
            key_left = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
            key_right = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            //todo : jump!
        }
        acc();
    }
    public void keyReleased(KeyEvent e, int frame)
    {
    	int key = e.getKeyCode();
		if (key == KeyEvent.VK_LEFT) {
            key_left = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            key_right = false;
        }
        acc();
    }
    public void releaseAll()
    {
        key_left = false;
        key_right = false;
        acc();
    }
}