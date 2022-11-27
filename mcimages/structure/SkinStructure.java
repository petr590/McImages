package mcimages.structure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import mcimages.AggregateDirection;
import mcimages.Context;
import mcimages.Direction;
import mcimages.block.Block;

public class SkinStructure extends Structure {
	
	public static final int IMAGE_SIDE_SIZE = 64;
	
	
	protected final ImageStructure[] sides;
	
	public SkinStructure(Context context, BufferedImage image) {
		final int
				width = image.getWidth(),
				height = image.getHeight();
		
		if(width != height || width % IMAGE_SIDE_SIZE != 0 || height % IMAGE_SIDE_SIZE != 0) {
			throw new IllegalArgumentException("Image must be square and the sides must be divided by 64 completely");
		}
		
		int ratio = width / IMAGE_SIDE_SIZE;
		
		Map<Integer, Block>
				horisontalPalette = ImageStructure.generatePalette(context.copyWithAggregateDirection(AggregateDirection.HORISONTAL), image),
				horisontalPalette2 = horisontalPalette, // TODO
				      downPalette = ImageStructure.generatePalette(context.copyWithAggregateDirection(AggregateDirection.DOWN), image),
						upPalette = ImageStructure.generatePalette(context.copyWithAggregateDirection(AggregateDirection.UP), image);
		
		this.sides = new ImageStructure[] {
				//																												offsetX		offsetY		offsetZ			startX		startY		endX		endY	  multRX multRY flying
				// Legs
				new ImageStructure(context.copyWithDirection(Direction.NORTH), image, horisontalPalette, horisontalPalette2, -17 * ratio, -32 * ratio,  -7 * ratio,    4 * ratio, 20 * ratio,  8 * ratio, 32 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.NORTH), image, horisontalPalette, horisontalPalette2, -29 * ratio,   0 * ratio,  -7 * ratio,   20 * ratio, 52 * ratio, 24 * ratio, 64 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.SOUTH), image, horisontalPalette, horisontalPalette2,  -4 * ratio, -32 * ratio,  10 * ratio,   12 * ratio, 20 * ratio, 16 * ratio, 32 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.SOUTH), image, horisontalPalette, horisontalPalette2, -24 * ratio,   0 * ratio,  10 * ratio,   28 * ratio, 52 * ratio, 32 * ratio, 64 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.WEST),  image, horisontalPalette, horisontalPalette2, -11 * ratio, -32 * ratio, -12 * ratio,    1 * ratio, 20 * ratio,  3 * ratio, 32 * ratio,    1,   1),
				new ImageStructure(context.copyWithDirection(Direction.EAST),  image, horisontalPalette, horisontalPalette2, -18 * ratio,   0 * ratio,   5 * ratio,   25 * ratio, 52 * ratio, 27 * ratio, 64 * ratio,    1,   1),
				
				new ImageStructure(context.copyWithDirection(Direction.DOWN),  image,       downPalette, -21 * ratio, -38 * ratio,   0 * ratio,    9 * ratio, 17 * ratio, 12 * ratio, 19 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.DOWN),  image,       downPalette, -33 * ratio,  -6 * ratio,   0 * ratio,   24 * ratio, 49 * ratio, 27 * ratio, 51 * ratio,   -1,   1),
				
				// Body
				new ImageStructure(context.copyWithDirection(Direction.NORTH), image, horisontalPalette, -33 * ratio, -20 * ratio,  -7 * ratio,   20 * ratio, 20 * ratio, 28 * ratio, 32 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.SOUTH), image, horisontalPalette, -28 * ratio, -20 * ratio,  10 * ratio,   32 * ratio, 20 * ratio, 40 * ratio, 32 * ratio,   -1,   1),
				
				// Hands
				new ImageStructure(context.copyWithDirection(Direction.NORTH), image, horisontalPalette, horisontalPalette2, -61 * ratio, -20 * ratio,  -7 * ratio,   44 * ratio, 20 * ratio, 48 * ratio, 32 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.NORTH), image, horisontalPalette, horisontalPalette2, -41 * ratio,  12 * ratio,  -7 * ratio,   36 * ratio, 52 * ratio, 40 * ratio, 64 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.SOUTH), image, horisontalPalette, horisontalPalette2, -52 * ratio, -20 * ratio,  10 * ratio,   52 * ratio, 20 * ratio, 56 * ratio, 32 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.SOUTH), image, horisontalPalette, horisontalPalette2, -32 * ratio,  12 * ratio,  10 * ratio,   44 * ratio, 52 * ratio, 48 * ratio, 64 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.WEST),  image, horisontalPalette, horisontalPalette2, -51 * ratio, -20 * ratio, -16 * ratio,   41 * ratio, 20 * ratio, 43 * ratio, 32 * ratio,    1,   1),
				new ImageStructure(context.copyWithDirection(Direction.EAST),  image, horisontalPalette, horisontalPalette2, -34 * ratio,  12 * ratio,   1 * ratio,   41 * ratio, 52 * ratio, 43 * ratio, 64 * ratio,    1,   1),
				
				new ImageStructure(context.copyWithDirection(Direction.DOWN),  image,       downPalette, -65 * ratio, -38 * ratio,  12 * ratio,   49 * ratio, 17 * ratio, 52 * ratio, 19 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.DOWN),  image,       downPalette, -45 * ratio,  -6 * ratio,  12 * ratio,   40 * ratio, 49 * ratio, 43 * ratio, 51 * ratio,   -1,   1),

				new ImageStructure(context.copyWithDirection(Direction.UP),    image,         upPalette, -61 * ratio, -38 * ratio, -23 * ratio,   45 * ratio, 17 * ratio, 48 * ratio, 19 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.UP),    image,         upPalette, -41 * ratio,  -6 * ratio, -23 * ratio,   36 * ratio, 49 * ratio, 39 * ratio, 51 * ratio,   -1,   1),
				
				// Head
				new ImageStructure(context.copyWithDirection(Direction.DOWN),  image,       downPalette, -29 * ratio, -52 * ratio,  24 * ratio,   17 * ratio,  1 * ratio, 23 * ratio,  7 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.UP),    image,         upPalette, -21 * ratio, -52 * ratio, -31 * ratio,    9 * ratio,  1 * ratio, 15 * ratio,  7 * ratio,   -1,   1),
				
				new ImageStructure(context.copyWithDirection(Direction.NORTH), image, horisontalPalette, horisontalPalette2, -21 * ratio, -24 * ratio,  -5 * ratio,    8 * ratio,  8 * ratio, 16 * ratio, 16 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.SOUTH), image, horisontalPalette, horisontalPalette2, -20 * ratio, -24 * ratio,  12 * ratio,   24 * ratio,  8 * ratio, 32 * ratio, 16 * ratio,   -1,   1),
				new ImageStructure(context.copyWithDirection(Direction.WEST),  image, horisontalPalette, horisontalPalette2, -13 * ratio, -24 * ratio,  -5 * ratio,    1 * ratio,  8 * ratio,  7 * ratio, 16 * ratio,    1,   1),
				new ImageStructure(context.copyWithDirection(Direction.EAST),  image, horisontalPalette, horisontalPalette2, -12 * ratio, -24 * ratio,  12 * ratio,   17 * ratio,  8 * ratio, 23 * ratio, 16 * ratio,    1,   1),
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