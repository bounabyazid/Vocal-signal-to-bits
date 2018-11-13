import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SIGNAL extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	public SIGNAL() throws HeadlessException {
		// TODO Auto-generated constructor stub
		super();
		initialize();
	}

	public SIGNAL(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public SIGNAL(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
		initialize();
	}

	public SIGNAL(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		initialize();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SIGNAL thisClass = new SIGNAL();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
				thisClass.repaint();
			}
		});
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(637, 370);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}
	
	public void paint(Graphics g) {
		String S = "";
		byte[] B = null;
		//String path = "C:/Users/POLO/Desktop/PGMS/SIGNAL VOCAL/src/Numero.wav";
		String path = "C:/Users/POLO/Desktop/PGMS/SIGNAL VOCAL/src/Blinck.wav";
		File f = new File(path);
		DataInputStream dis = null;
		byte[] data = null;
		try
	     {
	      data = new byte[(int)f.length()];
	      dis = new DataInputStream(new FileInputStream(f));
	      dis.readFully(data);
	      B = new byte[4];
	      int x1 = 0,y1 = 0,j = 1;
	      for(int i = 44;i<data.length;i=i+4)
	      {
	       B[0] = data[i];
	       B[1] = data[i+1];
	       B[2] = data[i+2];
	       B[3] = data[i+3];  
	       S = ByteBuffer.wrap(B).order(ByteOrder.LITTLE_ENDIAN).toString();
	       float Float = 0;
	       if(S.contains("NaN"))
	    	Float = 0;
	       else
	    	Float = ByteBuffer.wrap(B).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	       System.out.println(Float);
	       g.drawLine(x1, y1, j, (int)Float);
	       x1 = j;
	       y1 = (int)Float;
	       j++;
	      }
	     }catch(IOException e){}
	      finally{
	     	 if(dis != null)
	 			try {
	 				dis.close();
	 			} catch (IOException e) {}
	      }
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
