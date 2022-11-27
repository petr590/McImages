package mcimages.structure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import mcimages.Direction;
import mcimages.DirectionalContext;
import mcimages.block.Block;
import mcimages.AggregateDirection;
import mcimages.Context;

public class BlockStructure extends Structure {

	protected final ImageStructure[] sides;
	
	public BlockStructure(Context context, BufferedImage image) {
		final int
				width = image.getWidth(),
				height = image.getHeight();
		
		if(width != height)
			throw new IllegalArgumentException("The image must be square, but it has size " + width + "x" + height);
		
		final int
				edge = width,
				edgeM1 = edge - 1,
				mEdge = -edge;
		
		
		DirectionalContext
				upContext = context.copyWithDirection(Direction.UP),
				downContext = context.copyWithDirection(Direction.DOWN);
		
		Map<Integer, Block> horisontalPalette = ImageStructure.generatePalette(context.copyWithAggregateDirection(AggregateDirection.HORISONTAL), image,
				1, 1, image.getWidth() - 1, image.getHeight() - 1);
		
		this.sides = new ImageStructure[] {
				new ImageStructure(downContext, image, ImageStructure.generatePalette(downContext, image),       0, 0,         0),
				new ImageStructure(context.copyWithDirection(Direction.NORTH), image, horisontalPalette,         0, 0, mEdge,      0, 1, edgeM1, edgeM1),
				new ImageStructure(context.copyWithDirection(Direction.SOUTH), image, horisontalPalette, mEdge - 1, 0,     1,      1, 1, edge,   edgeM1),
				new ImageStructure(context.copyWithDirection(Direction.WEST),  image, horisontalPalette, mEdge - 1, 0, mEdge,      0, 1, edge,   edgeM1),
				new ImageStructure(context.copyWithDirection(Direction.EAST),  image, horisontalPalette,         0, 0,     1,      1, 1, edgeM1, edgeM1),
				new ImageStructure(upContext,   image,   ImageStructure.generatePalette(upContext, image),       0, 0, mEdge + 1),
		};
	}
	
	@Override
	public int minRenderDistance() {
		int minRenderDistance = 0;
		
		for(ImageStructure side : sides) {
			minRenderDistance = Math.max(minRenderDistance, side.minRenderDistance());
		}
		
		return minRenderDistance;
	}
	
	
	@Override
	public void writeTo(OutputStreamWriter out) throws IOException {
		for(ImageStructure side : sides) {
			side.writeTo(out);
		}
	}
}