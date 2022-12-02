package mcimages.context;

import mcimages.AggregateDirection;
import mcimages.Direction;
import mcimages.StructureType;
import mcimages.structure.Colorspace;

public abstract class Context {
	
	private final Colorspace colorspace;
	protected final StructureType structureType;
	
	private final int mcVersion;
	private boolean isOnSolid;
	
	private final boolean useIce, useBuddingAmethyst, useSeaLantern;
	
	
	protected Context(Colorspace colorspace, StructureType structureType, int mcVersion,
			boolean useIce, boolean useBuddingAmethyst, boolean useSeaLantern) {
		
		this(colorspace, structureType, mcVersion, true, useIce, useBuddingAmethyst, useSeaLantern);
	}
	
	protected Context(Colorspace colorspace, StructureType structureType, int mcVersion,
			boolean isOnSolid, boolean useIce, boolean useBuddingAmethyst, boolean useSeaLantern) {
		
		this.colorspace         = colorspace;
		this.structureType      = structureType;
		this.mcVersion          = mcVersion;
		this.isOnSolid          = isOnSolid;
		this.useIce             = useIce;
		this.useBuddingAmethyst = useBuddingAmethyst;
		this.useSeaLantern      = useSeaLantern;
	}
	
	protected Context(Context other) {
		this(
				other.colorspace, other.structureType, other.mcVersion, other.isOnSolid,
				other.useIce, other.useBuddingAmethyst, other.useSeaLantern
		);
	}
	
	
	/**
	 * {@literal true}, если блок находится на другом твёрдом блоке.
	 * Нужно для корректного размещения падающих блоков
	 */
	public boolean isOnSolid() {
		return isOnSolid;
	}
	
	
	public abstract AggregateDirection getAggregateDirection();
	
	
	public Colorspace getColorspace() {
		return colorspace;
	}
	
	public StructureType getStructureType() {
		return structureType;
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
	
	public AggregateContext copyForNotOnSolid() {
		// Copy value of the isLowerLayer field
		boolean isLowerLayer = this.isOnSolid;
		this.isOnSolid = false;
		
		AggregateContext newContext = new AggregateContext(this, AggregateDirection.HORISONTAL);
		
		// Restore value of the field
		this.isOnSolid = isLowerLayer;
		
		return newContext;
	}
}