package mcimages.block;

import mcimages.Direction;

public class ColumnEndBlock extends Block {

	public ColumnEndBlock(String id) {
		super(id);
	}
	
	public ColumnEndBlock(String id, int version) {
		super(id, version);
	}
	
	@Override
	protected String createPlaceState(Direction direction) {
		return id + "[axis=" + direction.axis.name + "]";
	}
}