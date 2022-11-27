package mcimages;

/**
 * Общее направление
 */
public enum AggregateDirection {
	HORISONTAL, UP, DOWN;

	public boolean isHorisontal() {
		return this == HORISONTAL;
	}

	public boolean isVertical() {
		return this != HORISONTAL;
	}
}