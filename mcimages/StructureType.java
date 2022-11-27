package mcimages;

import java.awt.image.BufferedImage;

import mcimages.structure.BlockStructure;
import mcimages.structure.ImageStructure;
import mcimages.structure.SkinStructure;
import mcimages.structure.Structure;

public enum StructureType {

	IMAGE {
		public Structure createStructure(DirectionalContext context, BufferedImage image) {
			return new ImageStructure(context, image);
		}
	},
	
	BLOCK {
		public Structure createStructure(DirectionalContext context, BufferedImage image) {
			return new BlockStructure(context, image);
		}
	},
	
	SKIN {
		public Structure createStructure(DirectionalContext context, BufferedImage image) {
			return new SkinStructure(context, image);
		}
	};
	
	public abstract Structure createStructure(DirectionalContext context, BufferedImage image);
}