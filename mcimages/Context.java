package mcimages;

import mcimages.structure.Colorspace;

public abstract class Context {

	private final Colorspace colorspace;
	protected final StructureType structureType;
	
	private final int mcVersion;
	
	private final boolean useIce, useBuddingAmethyst, useSeaLantern;
	
	protected Context(Colorspace colorspace, StructureType structureType, int mcVersion,
			boolean useIce, boolean useBuddingAmethyst, boolean useSeaLantern) {
		
		this.colorspace         = colorspace;
		this.structureType      = structureType;
		this.mcVersion          = mcVersion;
		this.useIce             = useIce;
		this.useBuddingAmethyst = useBuddingAmethyst;
		this.useSeaLantern      = useSeaLantern;
	}
	
	protected Context(Context other) {
		this(
				other.colorspace, other.structureType, other.mcVersion,
				other.useIce, other.useBuddingAmethyst, other.useSeaLantern
		);
	}
	
	
	public abstract AggregateDirection getAggregateDirection();
	
	public Colorspace getColorspace() {
		return colorspace;
	}
	
	public int getMcVersion() {
		return mcVersion;
	}
	
	public boolean useIce() {
		return useIce;
	}
	
	public boolean useBuddingAmethyst() {
		return useBuddingAmethyst;
	}
	
	public boolean useSeaLantern() {
		return useSeaLantern;
	}
	
	
	public DirectionalContext copyWithDirection(Direction direction) {
		return new DirectionalContext(this, direction);
	}
	
	public AggregateContext copyWithAggregateDirection(AggregateDirection aggregateDirection) {
		return new AggregateContext(this, aggregateDirection);
	}
}