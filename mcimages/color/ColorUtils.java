package mcimages.color;

public class ColorUtils {
	
	private ColorUtils() {}
	
	
	public static final double
			R_CF = 0.299,
			G_CF = 0.587,
			B_CF = 0.114;
	
	
	public static XYZ rgb2xyz(int rgb) {
		double  r = (rgb >>> 16)       / 255.0,
				g = (rgb >>> 8 & 0xFF) / 255.0,
				b = (rgb & 0xFF)       / 255.0;

		r = (r > 0.04045 ? Math.pow((r + 0.055) / 1.055, 2.4) : r / 12.92);
		g = (g > 0.04045 ? Math.pow((g + 0.055) / 1.055, 2.4) : g / 12.92);
		b = (b > 0.04045 ? Math.pow((b + 0.055) / 1.055, 2.4) : b / 12.92);

		return new XYZ(
			r * 0.4124 + g * 0.3576 + b * 0.1805,
			r * 0.2126 + g * 0.7152 + b * 0.0722,
			r * 0.0193 + g * 0.1192 + b * 0.9505
		);
	}
	
	public static int xyz2rgb(XYZ xyz) {
		double  x = xyz.a,
				y = xyz.b,
				z = xyz.c;
		
		double r = ( 3.2404542 * x - 1.5371385 * y - 0.4985314 * z),
			   g = (-0.9692660 * x + 1.8760108 * y + 0.0415560 * z),
			   b = ( 0.0556434 * x - 0.2040259 * y + 1.0572252 * z);

		r = r > 0.0031308 ? Math.pow(r, 1 / 2.4) * 1.055 - 0.055 : r * 12.92;
		g = g > 0.0031308 ? Math.pow(g, 1 / 2.4) * 1.055 - 0.055 : g * 12.92;
		b = b > 0.0031308 ? Math.pow(b, 1 / 2.4) * 1.055 - 0.055 : b * 12.92;

		return (int)(r * 255.0) << 16 | (int)(g * 255.0) << 8 | (int)(b * 255.0);
	}

	
	public static Lab xyz2lab(XYZ xyz) {
		double  l = f(xyz.b),
				L = 116.0 * l - 16.0,
				a = 500.0 * (f(xyz.a) - l),
				b = 200.0 * (l - f(xyz.c));
		
		return new Lab(L, a, b);
	}
	
	public static XYZ lab2xyz(Lab lab) {
		double  i = (lab.a + 16.0) * (1.0 / 116.0),
				x = fInv(i + lab.b * (1.0 / 500.0)),
				y = fInv(i),
				z = fInv(i - lab.c * (1.0 / 200.0));
		return new XYZ(x, y, z);
	}
	
	
	public static Lab rgb2lab(int rgb) {
		return xyz2lab(rgb2xyz(rgb));
    }
	
	public static int lab2rgb(Lab lab) {
		return xyz2rgb(lab2xyz(lab));
    }
	
	
	private static final double N = 4.0 / 29.0;
	
	private static double f(double x) {
		return x > 216.0 / 24389.0 ? Math.cbrt(x) : (841.0 / 108.0) * x + N;
	}
	
	private static double fInv(double x) {
		return x > 6.0 / 29.0 ? x*x*x : (108.0 / 841.0) * (x - N);
	}
	
	
	public static YCbCr rgb2YCbCr(int rgb) {
		double  r = rgb >>> 16,
				g = rgb >>> 8 & 0xFF,
				b = rgb & 0xFF; 
		
		return new YCbCr(
			  0.299 * r + 0.587  * g + 0.114  * b,
			-0.1687 * r - 0.3313 * g + 0.5    * b + 128,
			    0.5 * r - 0.4187 * g - 0.0813 * b + 128
		);
	}
}