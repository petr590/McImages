package mcimages;

import x590.util.function.TriIntFunction;

public enum Direction {

	NORTH("north", "south", AggregateDirection.HORISONTAL, Axis.X, (x, y, z) -> "~" +                x  + " ~" + coordToString( y) + " ~" + coordToString(-z)),
	SOUTH("south", "north", AggregateDirection.HORISONTAL, Axis.X, (x, y, z) -> "~" +               -x  + " ~" + coordToString( y) + " ~" + coordToString( z)),
	UP   ("up",    "down",  AggregateDirection.UP,         Axis.Y, (x, y, z) -> "~" +                x  + " ~" + coordToString(-z) + " ~" + coordToString(y + 1)),
	DOWN ("down",  "up",    AggregateDirection.DOWN,       Axis.Y, (x, y, z) -> "~" +                x  + " ~" + coordToString( z) + " ~" + coordToString(y + 1)),
	WEST ("west",  "east",  AggregateDirection.HORISONTAL, Axis.Z, (x, y, z) -> "~" + coordToString(-z) + " ~" + coordToString( y) + " ~" + -x),
	EAST ("east",  "west",  AggregateDirection.HORISONTAL, Axis.Z, (x, y, z) -> "~" + coordToString( z) + " ~" + coordToString( y) + " ~" +  x);
	
	
	private static String coordToString(int coord) {
		return coord == 0 ? "" : Integer.toString(coord);
	}
	
	
	public final String name, oppositeName;
	public final AggregateDirection aggregateDirection;
	public final Axis axis;
	
	private final TriIntFunction<String> getCoordsFunc;
	
	private Direction(String name, String oppositeName, AggregateDirection aggregateDirection, Axis axis, TriIntFunction<String> getCoordsFunc) {
		this.name = name;
		this.oppositeName = oppositeName;
		this.aggregateDirection = aggregateDirection;
		this.axis = axis;
		this.getCoordsFunc = getCoordsFunc;
	}
	
	public String getName() {
		return name;
	}
	
	public Axis getAxis() {
		return axis;
	}
	
	public boolean isVertical() {
		return axis.isVertical();
	}
	
	public boolean isHorisontal() {
		return axis.isHorisontal();
	}
	
	public String getCoords(int x, int y, int z) {
		return getCoordsFunc.apply(x, y, z);
	}
	
	@Override
	public String toString() {
		return name;
	}
}