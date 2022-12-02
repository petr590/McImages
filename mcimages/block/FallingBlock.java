package mcimages.block;

import mcimages.context.Context;

/**
 * Падающие блоки (песок, гравий и т.д.)
 */
public class FallingBlock extends Block {

	public FallingBlock(String id) {
		super(id);
	}
	
	public FallingBlock(String id, int version) {
		super(id, version);
	}
	
	@Override
	public boolean canUse(Context context) {
		return super.canUse(context) && context.getAggregateDirection().isHorisontal()
				&& !context.isOnSolid();
	}
}