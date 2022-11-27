package mcimages.color;

/**
 * Представляет цвет в каком-то цветовом пространстве
 */
public abstract class Color {

	public final double a, b, c;
	
	public Color(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
}