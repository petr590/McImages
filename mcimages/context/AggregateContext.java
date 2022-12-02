package mcimages.context;

import mcimages.AggregateDirection;

public class AggregateContext extends Context {
	
	private final AggregateDirection aggregateDirection;
	
	public AggregateContext(Context other, AggregateDirection aggregateDirection) {
		super(other);
		this.aggregateDirection = aggregateDirection;
	}
	
	@Override
	public AggregateDirection getAggregateDirection() {
		return aggregateDirection;
	}
}