package sagan;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import sagan.model.AuroraData;

public class Converter extends Component{

	/**
	 * @param args
	 */
	//Link references:
	//http://www.swpc.noaa.gov/pmap/GEpmap/GEpmapN.png
	//http://www.swpc.noaa.gov/pmap/GEpmap/GEpmapS.png
	
	/*
	 * // Get a pixel 
int rgb = bufferedImage.getRGB(x, y); 

// Get all the pixels 
int w = bufferedImage.getWidth(null); 
int h = bufferedImage.getHeight(null); 
int[] rgbs = new int[w*h]; bufferedImage.getRGB(0, 0, w, h, rgbs, 0, w); 
// Set a pixel 
rgb = 0xFF00FF00; 
// green 
bufferedImage.setRGB(x, y, rgb);
	 * 
	 */
	BufferedImage imgNorthPX, imgSouthPX;
	 
    public void paint(Graphics g) {
        //g.drawImage(img, 0, 0, null);
    }
 
    public AuroraData LoadAndConvert() {
    	/*
    	 * // Get a pixel
			int rgb = bufferedImage.getRGB(x, y);
			
			// Get all the pixels
			int w = bufferedImage.getWidth(null);
			int h = bufferedImage.getHeight(null);
			int[] rgbs = new int[w*h];
			bufferedImage.getRGB(0, 0, w, h, rgbs, 0, w);
			
			// Set a pixel
			rgb = 0xFF00FF00; // green
			bufferedImage.setRGB(x, y, rgb);
    	 */
		try {
			   URL northHemiURL = new URL("http://www.swpc.noaa.gov/pmap/GEpmap/GEpmapN.png");
			   URL southHemiURL = new URL("http://www.swpc.noaa.gov/pmap/GEpmap/GEpmapS.png");
			   imgNorthPX = ImageIO.read(northHemiURL);
			   imgSouthPX = ImageIO.read(southHemiURL);
			} catch (IOException e) {
			}
		
		//Now that you have a BufferImge instance to work on, access its pixels + return a 2-d array for each image
		int[][] n = manRGBArray(imgNorthPX);
		int [][] s = manRGBArray(imgSouthPX);
		
		AuroraData data = new AuroraData();
		data.setN(n);
		data.setS(s);
		return data;
    }
    
    private int[][] manRGBArray(BufferedImage image){
    	int[][] rgbArray = new int[400][400];
		int rgb = 3096;//Don't ask why
		int x=0;
		int y=0;
		for(x=0; x<400; x++){ //loop x axis of the image
			for(y=0; y<400; y++) {//loop y axis
				rgb = image.getRGB(x, y);
			    int alpha = ((rgb >> 24) & 0xff); 
			    int red = ((rgb >> 16) & 0xff); 
			    int green = ((rgb >> 8) & 0xff); 
			    int blue = ((rgb ) & 0xff); 
			    // Manipulate the r, g, b, and a values.
			    rgb = (alpha << 24) | (red << 16) | (green << 8) | blue; 
			    imgNorthPX.setRGB(x, y, rgb);
			    rgbArray[x][y] = rgb;
			}
		}
    return rgbArray;
}

 
//    public static void main(String[] args) {
// 
//        JFrame f = new JFrame("Load Image Sample");
//             
//        f.addWindowListener(new WindowAdapter(){
//                public void windowClosing(WindowEvent e) {
//                    System.exit(0);
//                }
//            });
// 
//        JApplet applet = new ImageOps();
//        f.getContentPane().add("Center", applet);
//        applet.init();
//        f.pack();
//        f.setSize(new Dimension(400,400));
//        f.add(new LoadImageApp());
//        f.pack();
//        f.setVisible(true);
//    }

}
