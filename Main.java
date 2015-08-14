import javax.sound.sampled.*;
import java.io.*;

import javax.sound.sampled.LineEvent.Type;
//throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException 
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame 
{
    static int deathcount = 0;
    static public String getErr(Exception ex)
    {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
    static Main instance;
	public Main(String arg)throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException 
	{
        instance = this;
        add(new Board(arg));

        setTitle(arg);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {    
            @Override
            public void run()
            {                
             	try{
	                JFrame ex = new Main(args[0]);
	                ex.setVisible(true);                
        		}catch (Exception e){
        			System.out.println(getErr(e));
        		}
        	}

        });
        //System.out.println("deathcount : "+deathcount + "you win!");
    }
}
/*
			
			smp.waitUntilDone();
*/