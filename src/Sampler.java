import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.LineEvent.Type;




class AudioListener implements LineListener {
    public boolean done = false;
    @Override public synchronized void update(LineEvent event) 
    {
		Type eventType = event.getType();
		if (eventType == Type.STOP || eventType == Type.CLOSE) 
		{
			done = true;
			notifyAll();
		}
    }

    public synchronized void waitUntilDone() throws InterruptedException 
    {
    	while (!done) { wait(); }
    }

}



public class Sampler
{
	AudioInputStream ais;
	AudioListener listener;
	Clip clip;
	WavReader wr;
	int sampleRate;
	public int hzpe,mxhz;//(hz/element) , max HZ

	void waitUntilDone()throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException
	{
		 listener.waitUntilDone();
	}
	void play()throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException
	{
		if(clip == null)
			System.out.println("wtf");
		clip.start();
	}
	void pause()throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException
	{clip.stop();}
	void stop()throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException
	{clip.stop();clip.setFramePosition(0);}

			

	public Sampler(String clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException
	{
		wr = new WavReader(new File(clipFile));


		listener = new AudioListener();
		ais = AudioSystem.getAudioInputStream(new File(clipFile));
		clip = AudioSystem.getClip();
		clip.addLineListener(listener);
		clip.open(ais);

		samples = wr.getSamples();
		//for(int i=0;i<samples.length;i++) System.out.println(samples[i]);
		sampleRate = wr.getSampleRate();

	}
	int[] samples;
	public int[] getSamples()
	{return samples;}
	

	public int getPlayingFrame()
	{
		return clip.getFramePosition();
	}


	
	private final int SAMLELEN = 8000;
	public int[] disppf()
	{
		int pf;	pf=(getPlayingFrame()-SAMLELEN/2);
		pf = Analysis.abs(pf);

		int[] sp;sp = new int[SAMLELEN];

		for(int i=0;i<SAMLELEN;i++)
		{
			sp[i] = samples[i+pf];
		//	System.out.println(""+sp[i]+"  "+(pf+i));
		}
//		System.out.println();
		return sp;
	}
	
	public void finalize()throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException
	{clip.close();ais.close();}
	 



}

/*
public int[] anliyzepf()
	{
		int pf=(getPlayingFrame()-SAMLELEN/2)<0?0:(getPlayingFrame()-SAMLELEN/2);
		//pf = pf%2==0?pf:pf-1;
		int[] sp = new int[SAMLELEN];
		for(int i=0;i<SAMLELEN;i+=1)
			sp[i] = samples[pf+i];
		return anliyze(sp);
	}
	public int[] anliyze()
	{
		int[] sp = new int[samples.length];
		for(int i=0;i<sp.length;i+=1)
			sp[i] = samples[i];
		return anliyze(samples);
	}

	public int indexToHz(int a)
	{
		return hzpe*a;
	}

	public int[] anliyze(int[] buf)
	{
		int[] ret = algo.four1(buf,buf.length/4,1);
		int[] retu = new int[ret.length];
		for(int i=0;i<ret.length;i+=1)
			retu[i] = ret[i]>0?ret[i]:-ret[i];
		System.arraycopy(ret,0,retu,0,retu.length);
		mxhz = sampleRate*2;
		hzpe = mxhz/ret.length/2;
		
		return ret;
	}
	*/