package mcimages.block;

import mcimages.AggregateDirection;
import mcimages.Context;

public class TopBlock extends Block {

	public TopBlock(String id) {
		super(id);
	}
	
	public TopBlock(String id, int version) {
		super(id, version);
	}

	@Override
	public boolean canUse(Context context) {
		return super.canUse(context) && context.getAggregateDirection() == AggregateDirection.UP;
	}
}