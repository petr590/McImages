package mcimages.block;

import java.util.function.Predicate;

import mcimages.context.Context;

public class IceBlock extends ConditionalBlock {
	
	private static final Predicate<Context> PREDICATE = context -> context.useIce();

	public IceBlock(String id) {
		super(id, PREDICATE);
	}

	public IceBlock(String id, int version) {
		super(id, version, PREDICATE);
	}
}