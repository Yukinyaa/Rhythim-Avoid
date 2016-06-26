package moe.meu.RAv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;
import java.awt.Toolkit;

import java.awt.event.*;
//import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;

import java.util.Arrays;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;
//import org.apache.commons.lang.ArrayUtils;


public class Board extends JPanel 
    implements ActionListener {

    public static final int B_WIDTH = 400;
    public static final int B_HEIGHT = 400;
    //public final int INITIAL_X = -40;
    //public final int INITIAL_Y = -40;
    private final int DELAY = 25;
    private final float SMOOTHFACTOR = 0.8f;
    /*private final*/static float SENSIVITY = 1.0f;
    boolean game;
    static Random random = new Random();
    int lastFrame = 0;
    private Timer timer;
    Sampler smp;
    Analysis anl;
    Character chara;
    float bufmx;
    String file;
    int savePoint;
    public Board(String arg) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException 
    {
        file = arg;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new TAdapter());
        savePoint = 0;
        smooth=0;
        //pause = 2;
        System.out.println("loaded!");

        smp = new Sampler(file);
        resetGame(0);
        
        //System.out.println(smp.anliyze());
        initBoard();        
        anl = new Analysis(smp.getSamples());
        
        playGame();
        bufmx = anl.maxPower;
        //System.out.println(  );
        //smp.play();
    }
    
    public void resetGame(int a)
    {
        
        
        try{
            smp.stop();
            Coldomain.rmall();
            smp.clip.setFramePosition(a);
        }catch (Exception asdf){}

        try{
            chara.rm();
        }catch (Exception asdf){}
        
        try{

            chara = new Character("asdf");
            
        }catch (Exception asdf){}
        lastFrame = 0;
        System.gc();
        
    }
    public void playGame()
    {
        try{
            smp.play();
        }catch (Exception asdf){}
        
    }
    int pause;
    private class TAdapter extends KeyAdapter {
        
        TAdapter()
        {
            super();
            pause=2;
        }
        public void keyReleased(KeyEvent e)
        {

            chara.keyReleased(e,smp.getPlayingFrame());
        }

        public void keyPressed(KeyEvent e)//   throws IOException, UnsupportedAudioFileException, InterruptedException 
        {
            try{
                int key = e.getKeyCode();
                //playGame();
                if(key == KeyEvent.VK_ESCAPE)
                {
                    smp.pause();
                    pause++;
                }
                if(key == KeyEvent.VK_SPACE)
                {
                    smp.play();
                }
                if(pause%2 == 0)
                {
                    smp.play();
                    chara.keyPressed(e,smp.getPlayingFrame());
                }
                else
                    chara.releaseAll();
            }catch (Exception asdf){}
        }
    }
    
    private void initBoard() 
    {
              
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(B_WIDTH-10, B_HEIGHT-10));

        setDoubleBuffered(true);

        
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(pause%2 == 0)
            render(g);
        if(pause == 0)
        {
            //todo : rave ruined
        }
    }

    int smooth;
    float sen;
    private void nouprender(Graphics g) {
        //todo : wqs
    }

    private void render(Graphics g) {
        // System.out.println();
        Graphics2D g2d = (Graphics2D) g;
        float[] buf;
        //buf = smp.disppf();

        buf = anl.getSoundPowerArrPM400(smp.getPlayingFrame());

        /*
        for(int i=0;i<buf.length;i++)
            buf[i] = buf[i]>0?buf[i]:-buf[i];
        if(smooth == null) 
        {
            smooth = new int[buf.length];
            for(int i=0;i<buf.length;i++)
                smooth[i] = buf[i];
        }
        else
            for(int i=0;i<buf.length;i++)
                smooth[i] = (int)( 
                    (smooth[i]*SMOOTHFACTOR) + (buf[i]*(1f-SMOOTHFACTOR))
                    );
        */
	    //System.out.println(bufmx);
	    //bufmx=100000;


	    float barwidth = B_HEIGHT/buf.length;
        

        //g.setColor(Color.green);
        //for(int i=0;i<buf.length;i++)
        //{
        //    int height = (int)((buf[i]/bufmx)*B_HEIGHT);
        //   g.fillRect((int)barwidth*i,(B_HEIGHT),(int)((barwidth>1)?barwidth:1),-(height));
        //}

        
        //if((anl.getFact((smp.getPlayingFrame()))/anl.maxFact)>=0)
        //    System.out.println( (anl.getFact((smp.getPlayingFrame()))/anl.maxFact)  );
        //System.out.println(anl.getFact(smp.getPlayingFrame())+"/"+anl.maxFact);


        for(; lastFrame<smp.getPlayingFrame();lastFrame+=anl.windowsize)
        {
            float curruntFact = (anl.getFact((lastFrame+36000))/anl.maxFact);
                //System.out.println( (anl.getFact((smp.getPlayingFrame()+18000))/anl.maxFact)  );
            sen *= 0.993092f;//^100 ~= 0.5 (
                        //0.917f^8 ~= 0.5)
            if(curruntFact>sen*SENSIVITY)
            {
                sen = curruntFact*1.2f;
                new Drop
                (
                    lastFrame+36000,
                    random.nextInt(B_WIDTH-Drop.defaultwidth-50),
                    (int)(Drop.defaultwidth*(anl.getFact((lastFrame+18000))/anl.maxFact)*2),
                    20000
                );
            }
        }
        g.setColor(Color.red);
        Drop.renderall(g,smp.getPlayingFrame());
            

        float volpower = anl.getSoundPower(smp.getPlayingFrame())/anl.maxPower;//Math.log(anl.getSoundPower(smp.getPlayingFrame()))/Math.log(anl.maxPower);
        //System.out.println(anl.getSoundPower(smp.getPlayingFrame()));

        g.setColor(Color.white);
        g.fillRect(B_WIDTH-20,0,20,B_HEIGHT);
        g.fillRect(0,0,20,B_HEIGHT);

        g.setColor(Color.cyan);
        g.fillRect(B_WIDTH-20+1,(int)(B_HEIGHT*(1-volpower))+1,
            20-2,(int)(B_HEIGHT*(volpower))-2);
        g.fillRect(1,(int)(B_HEIGHT*(1-volpower))+1,
            20-2,(int)(B_HEIGHT*(volpower))-2);
        
        g.setColor(Color.cyan);
        chara.render(g,smp.getPlayingFrame());
        
        
        float percent;
        percent = (float)smp.getPlayingFrame()/smp.getSamples().length;
        
        Coldomain.renderAll(g);
        g.setColor(Color.white);
        g.fillRect(0,0,(int)(B_WIDTH*percent),10);
        g.setColor(Color.gray);
        g.drawRect(0,0,B_WIDTH,10);

        if(savePoint<=(int)(percent*10)*smp.getSamples().length/10)
            System.out.println("Game Saved :" +(percent*100)+ "percent, "+ (savePoint = smp.getPlayingFrame()));

        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
        if(chara.check())
        {
            System.out.println("death #"+(++Main.deathcount)+","+(percent*100)+"percent");
            resetGame(savePoint);
            pause=0;

            //game over
        }
        //if(smp.listener.done)
        //{
        //    pullThePlug();
        //}

    }
    public void pullThePlug() 
    {
        //Main.instance.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();  
    }
    
    
}