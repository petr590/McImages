package mcimages.block;

import mcimages.context.Context;

public class TopBottomBlock extends Block {

	public TopBottomBlock(String id) {
		super(id);
	}
	
	public TopBottomBlock(String id, int version) {
		super(id, version);
	}
	
	@Override
	public boolean canUse(Context context) {
		return super.canUse(context) && context.getAggregateDirection().isVertical();
	}
}