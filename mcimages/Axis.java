package mcimages;

public enum Axis {

	X("x", false),
	Y("y", true),
	Z("z", false);
	
	public final String name;
	public final boolean isVertical;
	
	private Axis(String name, boolean isVertical) {
		this.name = name;
		this.isVertical = isVertical;
	}

	public String getName() {
		return name;
	}

	public boolean isHorisontal() {
		return !isVertical;
	}

	public boolean isVertical() {
		return isVertical;
	}

	@Override
	public String toString() {
		return name;
	}
}