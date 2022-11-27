package mcimages.block;

import mcimages.Direction;

/**
 * Ножка гриба
 */
public class BlockInside extends Block {

	public BlockInside(String id) {
		super(id);
	}
	
	public BlockInside(String id, int version) {
		super(id, version);
	}
	
	@Override
	protected String createPlaceState(Direction direction) {
		return id + "[" + direction.oppositeName + "=false]";
	}
}