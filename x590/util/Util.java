package x590.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class Util {
	
	
	public static boolean askUser(String message) {
		System.out.print(message);
		
		Scanner systemIn = new Scanner(System.in);
		
		String answer = systemIn.next().toLowerCase();
		
		systemIn.close();
		
		switch(answer) {
			case "", "y", "yes": return true;
			default: return false;
		}
	}
	
	
	public static BufferedImage resizeImage(Image image, int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		
		graphics2D.setComposite(AlphaComposite.Src);
		// Below three lines are for RenderingHints for better image quality at cost of higher processing time
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,     RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
		
		graphics2D.drawImage(image, 0, 0, width, height, null);
		graphics2D.dispose();
		
		return bufferedImage;
	}
}