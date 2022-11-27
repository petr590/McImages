package mcimages.structure;

import java.util.List;
import java.util.Map.Entry;
import java.util.function.IntFunction;

import mcimages.block.Block;
import mcimages.color.Color;
import mcimages.color.ColorUtils;

public enum Colorspace {
	
	RGB {
		@Override
		public Block getBlockForColor(int rgb1, List<Entry<Integer, Block>> blocks) {
			int r1 = rgb1 >>> 16,
				g1 = rgb1 >>> 8 & 0xFF,
				b1 = rgb1 & 0xFF;
			
			double minDiff = Double.MAX_VALUE;
			Block nearestBlock = null;
			
			for(Entry<Integer, Block> entry : blocks) {
				int rgb2 = entry.getKey();
				Block block = entry.getValue();
				
				if(rgb1 == rgb2) {
					return block;
				}
				
				int r = (rgb2 >>> 16)       - r1,
					g = (rgb2 >>> 8 & 0xFF) - g1,
					b = (rgb2 & 0xFF)       - b1;
				
				double diff = Math.sqrt(r * r * (R_CF * R_CF) + g * g * (G_CF * G_CF) + b * b * (B_CF * B_CF));
				
				if(diff < minDiff) {
					minDiff = diff;
					nearestBlock = block;
				}
			}
			
			return nearestBlock;
		}
	},
	
	
	XYZ {
		@Override
		public Block getBlockForColor(int rgb, List<Entry<Integer, Block>> blocks) {
			return getBlockForColor(rgb, blocks, ColorUtils::rgb2xyz);
		}
	},
	
	
	LAB {
		@Override
		public Block getBlockForColor(int rgb, List<Entry<Integer, Block>> blocks) {
			return getBlockForColor(rgb, blocks, ColorUtils::rgb2lab);
		}
	},
	
	
	YCbCr {
		@Override
		public Block getBlockForColor(int rgb, List<Entry<Integer, Block>> blocks) {
			return getBlockForColor(rgb, blocks, ColorUtils::rgb2YCbCr);
		}
	};
	
	public static final double
			R_CF = 0.299,
			G_CF = 0.587,
			B_CF = 0.114;
	
	public abstract Block getBlockForColor(int rgb, List<Entry<Integer, Block>> blocks);
	
	
	private static Block getBlockForColor(int rgb1, List<Entry<Integer, Block>> blocks, IntFunction<? extends Color> colorConverter) {
		Color color1 = colorConverter.apply(rgb1);
		double  a1 = color1.a,
				b1 = color1.b,
				c1 = color1.c;
		
		double minDiff = Double.MAX_VALUE;
		Block nearestBlock = null;
		
		for(Entry<Integer, Block> entry : blocks) {
			int rgb2 = entry.getKey();
			Block block = entry.getValue();
			
			if(rgb1 == rgb2) {
				return block;
			}
			
			Color color2 = colorConverter.apply(rgb2);
			
			double  a = color2.a - a1,
					b = color2.b - b1,
					c = color2.c - c1;
			
			double diff = Math.sqrt(a * a + b * b + c * c);
			
			if(diff < minDiff) {
				minDiff = diff;
				nearestBlock = block;
			}
		}
		
		return nearestBlock;
	}
}