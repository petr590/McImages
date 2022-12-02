package mcimages.block;

import mcimages.AggregateDirection;
import mcimages.context.Context;

public class BottomBlock extends Block {

	public BottomBlock(String id) {
		super(id);
	}
	
	public BottomBlock(String id, int version) {
		super(id, version);
	}
	
	@Override
	public boolean canUse(Context context) {
		return super.canUse(context) && context.getAggregateDirection() == AggregateDirection.DOWN;
	}
}