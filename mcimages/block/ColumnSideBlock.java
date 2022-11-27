package mcimages.block;

import mcimages.Direction;

public class ColumnSideBlock extends Block {

	public ColumnSideBlock(String id) {
		super(id);
	}
	
	public ColumnSideBlock(String id, int version) {
		super(id, version);
	}

	@Override
	protected String createPlaceState(Direction direction) {
		return direction.isHorisontal() ? id : id + "[axis=x]";
	}
}