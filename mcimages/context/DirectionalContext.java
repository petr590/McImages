package mcimages.context;

import java.awt.image.BufferedImage;

import mcimages.AggregateDirection;
import mcimages.Direction;
import mcimages.StructureType;
import mcimages.structure.Structure;

import x590.argparser.ArgsNamespace;

public class DirectionalContext extends Context {
	
	private final Direction direction;
	
	public DirectionalContext(ArgsNamespace arguments, StructureType structureType) {
		super(arguments.get("--colorspace"),
				structureType,
				arguments.getInt("--version"),
				arguments.getBoolean("--ice"),
				arguments.getBoolean("--amethyst"),
				arguments.getBoolean("--sea-lantern")
		);
		
		System.out.println("Colorspace = " + getColorspace());
		
		this.direction = arguments.get("--direction");
	}
	
	public DirectionalContext(Context other, Direction direction) {
		super(other);
		this.direction = direction;
	}
	
	
	public Direction getDirection() {
		return direction;
	}
	
	@Override
	public AggregateDirection getAggregateDirection() {
		return direction.aggregateDirection;
	}
	
	
	public Structure createStructure(BufferedImage image) {
		return getStructureType().createStructure(this, image);
	}
}