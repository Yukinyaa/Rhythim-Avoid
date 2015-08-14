package tk.yukinyaa.RAv;
public class Analysis
{ 
	float soundPower[];
	float maxPower;
	float fact[];
	float sigfact;
	float maxFact;
	int fpb;
	public static final int windowsize = 300;
	public static final int cmpWidth = 10;

	Analysis(int[] data)
	{
		soundPower = new float[data.length/windowsize];
		sigfact = 0f;
		fact = new float[soundPower.length-(cmpWidth*2+1)];
		for(int i=0; i<soundPower.length; i++)
			soundPower[i] = calcsoundpower(data,i*windowsize,windowsize);

		for(int i=0; i<soundPower.length-(cmpWidth*2+1); i++)
		{
			fact[i] = Float.POSITIVE_INFINITY;
			do{
				int br=0;
				for(int j=0;
						j<cmpWidth*2+1;
						j++)
					if(soundPower[i+j] > soundPower[i+cmpWidth]*1.1f)
						br++;
				if(br!=0)
					break;
				fact[i]=0;
				{
					float left=0,right=0;
					for(int j=0;
						j<cmpWidth;
						j++)	left+=soundPower[i+j];
					for(int j=cmpWidth+1;
						j<cmpWidth*2+1;
						j++)	right+=soundPower[i+j];
					if(left>right)
						break;
					fact[i] = soundPower[i+cmpWidth]/(right+left)/cmpWidth;
					//if(fact[i]>0)
					//	fact[i] = (float)root(fact[i],20);
				}
				//fact[i] =
			}while(false);
			if(fact[i] == Float.POSITIVE_INFINITY)
				fact[i] = 0;
			//fact[i] = soundPower[i+cmpWidth];
			//System.out.println(fact[i]);
			sigfact += fact[i];
			if(maxFact<fact[i])
				System.out.println(maxFact = fact[i]);
		}
	}



	static public int getWindowNumber(int frame)
	{
		return frame/windowsize;
	}
	public float getFact(int frame)
	{
		try{
			return fact[getWindowNumber(frame)+cmpWidth];
		} catch(ArrayIndexOutOfBoundsException aioobe) {
			return 0; 
		}
	}
	public float[] getSoundPowerArrPM400(int frame)
	{
		int pm = Board.B_WIDTH/5;
		float[] ret = new float[pm];
		for(int i=0;i<pm;i++)
			ret[i]=(getSoundPower
					(frame+
						windowsize*(i-(pm/2))
					));
		return ret;
	}
	public float getSoundPower(int frame)
	{
		try
		{
			return soundPower[getWindowNumber(frame)];
		}
		catch(ArrayIndexOutOfBoundsException aioobe)
		{
			//System.out.println("framespAIOOBE"+frame);
			return 0;
		}
	}
	//private void calc(int[] data){
				

  		//return fpb;
  	//}
 	private float calcsoundpower(int[] data, int offset, int len){
 		float avg = 0;
 		float max = Integer.MIN_VALUE;
 		float min = Integer.MAX_VALUE;
 		for (int i=0; i<len ; i++ ) {
 			if(max < data[offset+i])
 				max = data[offset+i];
 			if(min > data[offset+i])
 				min = data[offset+i];
 			avg+=data[offset+i];
 		}
 		//System.out.println(max+" "+min);
 		avg/=len;
 		max -= min;
 		if(maxPower<max)
				maxPower = max;
 		return max;
 	}
 	public static double root(double num, double root)
	{
		return Math.pow(Math.E, Math.log(num)/root);
	} 
 	public static int abs(int a)
 	{
		return a>0?a:-a;
 	}
 	public static float abs(float a)
 	{
		return a>0?a:-a;
 	}
 }