package mcimages.block;

import mcimages.Direction;

public class TrapdoorBlock extends Block {

	public TrapdoorBlock(String id) {
		super(id);
	}

	public TrapdoorBlock(String id, int version) {
		super(id, version);
	}
	
	@Override
	protected String createPlaceState(Direction direction) {
		return id + (direction.isHorisontal() ?
				"[facing=" + direction.name + ",open=true]" :
				direction == Direction.UP ? "[half=top]" : "");
	}
}