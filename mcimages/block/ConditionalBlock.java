package mcimages.block;

import java.util.function.Predicate;

import mcimages.context.Context;

/**
 * Блок используется только при выплнениии
 * определённых кастомных условий, заданных через {@link #predicate}.
 */
public class ConditionalBlock extends Block {
	
	private final Predicate<Context> predicate;

	public ConditionalBlock(String id, Predicate<Context> predicate) {
		super(id);
		this.predicate = predicate;
	}
	
	public ConditionalBlock(String id, int version, Predicate<Context> predicate) {
		super(id, version);
		this.predicate = predicate;
	}
	
	@Override
	public boolean canUse(Context context) {
		return super.canUse(context) && predicate.test(context);
	}
}