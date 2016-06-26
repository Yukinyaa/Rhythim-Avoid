package moe.meu.RAv;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Coldomain{
    int x1,y1;
    int x2,y2;
    boolean ison;
    static List<Coldomain> list = new ArrayList<Coldomain>();
    Coldomain(int x1_, int y1_, int x2_, int y2_)
    {
    	x1 = x1_;
    	x2 = x2_;
    	y1 = y1_;
    	y2 = y2_;
    	list.add(this);
        ison = false;
    }
    public String toString()
    {
        return "{"+x1+","+y1+","+x2+","+y2+"}";
    }
    static boolean cmp(Coldomain a,Coldomain b)
    {
        if(!((a.ison)&&(b.ison)))
            return false;

        if( a.x1 > b.x2 || b.x1 > a.x2)
        {
            //if( b.y2 < a.y1 || a.y2 < b.y1 )
            {
                
                return false;
            }
            //return true;
        }
        
        //System.out.println("( "+a+" , "+b+" )");
        return true;
    }
    static void rm(Coldomain tar)
	{
		list.remove(tar);
	}
    static void rmall()
    {
        list = new ArrayList<Coldomain>();
        Drop.rmall();
        System.gc();
    }
    static void rm(int tar)
    {
        list.remove(tar);
    }

	static boolean chk(Coldomain tar)
	{
        int index = list.indexOf(tar);

        try{
            for(int i=0;;i++)
                if(i!=index&&cmp(list.get(i),tar))//
                    return true;
        }
        catch(IndexOutOfBoundsException ioobe)
        {}
        return false;
	}
    static void renderAll(Graphics g)
    {
         try{
            for(int i=0;;i++)
                list.get(i).colRender(g);
        }
        catch(IndexOutOfBoundsException ioobe)
        {}
    }
    public void colRender(Graphics g)
    {
        if(ison)
            g.drawRect(x1,y1,x2-x1,y2-y1);
    }
    //static public bool ColRender(Graphics g,Coldomain n)
    //public bool doescolide(coldomain 2)
}
