import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.List;

public class Drop extends Coldomain{
    int w,x;
    static List<Drop> list = new ArrayList<Drop>();
    static int defaultwidth = 30;
    int dropdelay = 2000;
    int tarframe;
    public Drop(int targetframe,int x,int w,int dropdelay)
    {
        super(x, 0, x+30, Board.B_HEIGHT);
        
        if(w==0)
            this.w=defaultwidth;
        else
            this.w=w;
        ison = false;
        this.x = x;
        
        list.add(this);
        this.dropdelay = dropdelay;
        tarframe = targetframe;
    }
    static void rm(int i)
    {
        rm((Coldomain)list.get(i));
        list.remove(i);
    }
    static void rmall()
    {
        list = new ArrayList<Drop>();
        //System.gc(); 
    }
    static void renderall(Graphics g, int frame)
    {
        try{
            g.setColor(new Color(100,100,100));
            g.fillRect(0,Board.B_WIDTH,50,50);
            for(int i=0;;i++)
                while(list.get(i).render(g,frame))
                    rm(i);
        }
        catch(IndexOutOfBoundsException ioobe)
        {}

    }
    public boolean render(Graphics g, int frame)
    {

        //System.out.println("asdf");
        int framediff = tarframe-frame;
        int c = Board.random.nextInt(768);
        
        

        int r_=255-(c<256?255-c:c>=512?c-512:0),
            g_=255-(c<512?c-256:c<=512?512-c:0),
            b_=255-(c>256?c<=512?c-256:767-c:0);
        try{
        g.setColor(
            new Color(
                r_>255?255:r_,
                g_>255?255:g_<0?0:g_,
                b_>255?255:b_
                ));
        }catch(Exception e){}  
        if(framediff<-dropdelay/5)
            return true;
        else if (framediff < 0)
            {
                g.fillRect(x,0,w,Board.B_HEIGHT);
                ison = true;
            }
        else //if(framediff > 2000)
            g.fillRect(x,0,w,-framediff/(dropdelay/20)+50);
        return false;
    }
}