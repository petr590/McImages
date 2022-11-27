package mcimages.block;

import mcimages.Context;

public class SideBlock extends Block {

	public SideBlock(String id) {
		super(id);
	}
	
	public SideBlock(String id, int version) {
		super(id, version);
	}
	
	@Override
	public boolean canUse(Context context) {
		return super.canUse(context) && context.getAggregateDirection().isHorisontal();
	}
}