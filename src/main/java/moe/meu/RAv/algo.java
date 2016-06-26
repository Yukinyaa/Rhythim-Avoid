package moe.meu.RAv;

public class algo
{ 
  static public int[] four1(int datai[], int nn, int isign) {
   
    float data[] = new float[datai.length];
    for(int iii=0; iii<datai.length; iii++)
      data[iii] = datai[iii];

    int i, j, n, mmax, m, istep;
    float wtemp, wr, wpr, wpi, wi, theta, tempr, tempi;
   
    n = nn << 1;
    j = 1;
    for(i = 1; i < n; i += 2) {
      if(j > i) {
        float temp;
        temp = data[j];
        data[j] = data[i];
        data[i] = temp;
        temp = data[j+1];
        data[j+1] = data[i+1];
        data[i+1] = temp;
      }
      m = n >> 1;
      while(m >= 2 && j > m) {
        j -= m;
        m >>= 1;
      }
      j += m;
    }
    mmax = 2;
    while(n > mmax) {
      istep = (mmax << 1);
      theta = (float)(isign*(6.28318530717959/mmax));
      wtemp = (float)Math.sin((0.5*theta));
      wpr = -2.0f*wtemp*wtemp;
      wpi = (float)Math.sin(theta);
      wr = 1.0f;
      wi = 0.0f;
      for(m = 1; m < mmax; m += 2) {
        for(i = m; i <= n; i += istep) {
          j = i+mmax;
          tempr = wr*data[j]-wi*data[j+1];
          tempi = wr*data[j+1]+wi*data[j];
          data[j] = data[i] - tempr;
          data[j+1] = data[i+1] - tempi;
          data[i] += tempr;
          data[i+1] += tempi;
        }
      wr = (wtemp=wr)*wpr-wi*wpi+wr;
      wi = wi*wpr+wtemp*wpi+wi;
      }
      mmax = istep;
    }
    for(int iii=0; iii<datai.length; iii++)
      datai[iii] = (int)data[iii];
    return datai;
  }
}