import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BYTEFILE 
{
	public long ChunkSize = 0,
	           Subchunk1Size = 0,
	           AudioFormat = 0,
	           NumChannels = 0,
	           SampleRate = 0,
	           ByteRate = 0,
	           BlockAlign = 0,
	           BitsPerSample = 0,
	           Subchunk2Size = 0;
	
	public String ChunkID = "",
	              Format = "",
	              Subchunk1ID = "",
	              Subchunk2ID = "";
	//-----------------------------------------
	String S = "";
	byte[] B = null;//new byte[4];
	//String path = "C:/Users/POLO/Desktop/PGMS/SIGNAL VOCAL/src/Numero.wav";
	String path = "C:/Users/POLO/Desktop/PGMS/SIGNAL VOCAL/src/Blinck.wav";
	File f = new File(path);
	
	DataInputStream dis = null;
	byte[] data = null;
	
	public BYTEFILE() 
	{
     try
     {
      data = new byte[(int)f.length()];
      dis = new DataInputStream(new FileInputStream(f));
      dis.readFully(data);
     
      int i = 0;
      //-----------------RIFF----------------------------1-4
      for(i=0;i<4;i++)
       ChunkID += (char)data[i];  
      //-----------------LONGEUR WAV---------------------5-8
      B = new byte[4];
  	    
      for(i=4;i<8;i++)
       B[i-4] = data[i];//System.out.print((int)data[i]);
      
      Reverser_Byte(B);
      ChunkSize = ByteArrayToInt(B)+8;
      //-----------------WAVE------------------------9-12
      for(i=8;i<12;i++)
       Format += (char)data[i];
      //-------------------fmt-------------------------13-16      
      for(i=12;i<16;i++)
       Subchunk1ID += (char)data[i];
      //----------------nombre d'octets----------------17-20
      for(i=16;i<20;i++)
       B[i-16] = data[i];//System.out.print((int)data[i]);
       
      Reverser_Byte(B);
      Subchunk1Size = byteArrayToInt(B);

      //----------------numéro de format---------------21-22
      //for(i=20;i<22;i++)//1:PCM...
      B[0] = 00;
      B[1] = 00;
      B[2] = data[21];
      B[3] = data[20];  
      AudioFormat = byteArrayToInt(B);
      //----------------nombre de canaux---------------23-24
      //for(i=22;i<24;i++)//1 : Mono, 2 : Stereo, 3 : Stereo + centre, 4 : Avant droite et gauche, arrière droite et gauche, 
      B[0] = 00;
      B[1] = 00;
      B[2] = data[23];
      B[3] = data[22];  
      NumChannels = byteArrayToInt(B);//5 : Surrond (3 avant, 2 arrières), 6 : Droite et gauche, centre droite et gauche, 2 arrière)
       
      //------------fréquence échantillon Hz-----------25-28
      for(i=24;i<28;i++)
       B[i-24] = data[i];//System.out.print((int)data[i]);
      
      Reverser_Byte(B);
      SampleRate = ByteArrayToInt(B);
      
      //----------------Nombre d'octets/Sec---------------29-32
      B = new byte[4];
      for(i=28;i<32;i++)//Nombre d'octets par seconde de musique
       B[i-28] = data[i];
      Reverser_Byte(B);
      ByteRate = ByteArrayToInt(B);
      //---------------------BlockAlign-------------------33-34
      B[0] = 00;
      B[1] = 00;
      B[2] = data[33];
      B[3] = data[32];  
      BlockAlign = ByteArrayToInt(B);
      //------------Nombre d'octets/échantillon-----------35-36
      B[0] = 00;
      B[1] = 00;
      B[2] = data[35];
      B[3] = data[34];  
      BitsPerSample = ByteArrayToInt(B);
      //----------------------------------------------------------
      for(i=36;i<40;i++)
       Subchunk2ID += (char)data[i];
      //------------Subchunk2Size----------------------------36-40
      B = new byte[4];
      for(i=40;i<44;i++)
       B[i-40] = data[i];
      Reverser_Byte(B);
      Subchunk2Size = ByteArrayToInt(B);
      
      B = new byte[4];
      //for(i=44;i<47;i++)
      // B[i-44] = data[i];
      Reverser_Byte(B);
      //for(byte b : data)
      for(i = 44;i<data.length;i=i+4)
      {
       B[0] = data[i];
       B[1] = data[i+1];
       B[2] = data[i+2];
       B[3] = data[i+3];   
       //byte[] bytes = {0,0,data[i],data[i+1]};
       float f = ByteBuffer.wrap(B).order(ByteOrder.LITTLE_ENDIAN).getFloat();
       System.out.println(f);
       //System.out.print((char)b);
       //System.out.printf("   0x%02x",b);
       //System.out.print("   "+Integer.toOctalString(b));
       //System.out.println("   "+Integer.toBinaryString(b));
      }
      
      //ByteRate = SampleRate * NumChannels * BitsPerSample/8;
      //BlockAlign = NumChannels * BitsPerSample/8;
      //Subchunk2Size	= NumSamples *NumChannels *BitsPerSample/8;
            
      /*System.out.println("ChunkID"+" = "+ChunkID+"\n"+
    		             "ChunkSize"+" = "+ChunkSize+"\n"+
    		             "Format"+" = "+Format+"\n"+
    		            
    		             "Subchunk1ID"+" = "+Subchunk1ID+"\n"+
    		             "Subchunk1Size"+" = "+Subchunk1Size+"\n"+
    		             "AudioFormat"+" = "+AudioFormat+"\n"+
    		             "NumChannels"+" = "+NumChannels+"\n"+
    		             "SampleRate"+" = "+SampleRate+"\n"+
    		             "ByteRate"+" = "+ByteRate+"/"+(SampleRate *NumChannels *BitsPerSample/8)+"\n"+
    	                 "BlockAlign"+" = "+BlockAlign+"/"+NumChannels * BitsPerSample/8+"\n"+
    		             "BitsPerSample"+" = "+BitsPerSample+"\n"+
    		             
    		             "Subchunk2ID"+" = "+Subchunk2ID+"\n"+
    		             "Subchunk2Size"+" = "+Subchunk2Size+" / ");//+(NumSamples *NumChannels *BitsPerSample/8));
      //System.out.println(ByteArrayToInt(B));
      System.out.println((Subchunk2Size*8/(NumChannels *BitsPerSample)));  */   
     }
     catch(IOException e){}
     finally{
    	 if(dis != null)
			try {
				dis.close();
			} catch (IOException e) {}
     }
	}

	public static void main(String[] args) 
	{

     new BYTEFILE();
	}
	
	public static int ByteArrayToInt(byte[] b) 
	{
	    int value = 0;
	    for (int i = 0; i < 4; i++) {
	        int shift = (4 - 1 - i) * 8;
	        value += (b[i] & 0x000000FF) << shift;
	    }
	    return value;
	}
	
	public int byteArrayToInt(byte[] b) 
	{
	  if (b.length == 4)
       return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8 | (b[3] & 0xff);
      else   
       if (b.length == 2)
		return 0x00 << 24 | 0x00 << 16 | (b[0] & 0xff) << 8 | (b[1] & 0xff);

      return 0;
	}
	
	public void Reverser_Byte(byte[] B)
	{ 
	  byte[] F = new byte[B.length];
	   
	  for(int i=0;i<B.length;i++)
	  {
	   byte b = F[3-i]; 
	   F[i] = B[3-i];  
	  }
	  for(int i=0;i<B.length;i++)
	   B[i] = F[i]; 
	}
}
