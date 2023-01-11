package mcimages;

import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

import mcimages.context.DirectionalContext;
import mcimages.structure.BlockStructure;
import mcimages.structure.ImageStructure;
import mcimages.structure.SkinStructure;
import mcimages.structure.Structure;

/**
 * Тип структуры - изображение, блок или скин 
 */
public enum StructureType {
	IMAGE(ImageStructure::new),
	BLOCK(BlockStructure::new),
	SKIN(SkinStructure::new);
	
	private final BiFunction<DirectionalContext, BufferedImage, Structure> structureCreator;
	
	private StructureType(BiFunction<DirectionalContext, BufferedImage, Structure> structureCreator) {
		this.structureCreator = structureCreator;
	}
	
	public Structure createStructure(DirectionalContext context, BufferedImage image) {
		return structureCreator.apply(context, image);
	}
}