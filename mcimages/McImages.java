package mcimages;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import argparser.ArgsNamespace;
import argparser.ArgumentParseException;
import argparser.Flag;
import argparser.StandartArgParser;
import argparser.Times;
import argparser.option.EnumOption;
import argparser.option.IntOption;
import argparser.option.StringOption;
import mcimages.block.Block;
import mcimages.block.BlockInside;
import mcimages.block.BottomBlock;
import mcimages.block.ColumnEndBlock;
import mcimages.block.ColumnSideBlock;
import mcimages.block.ConditionalBlock;
import mcimages.block.FallingBlock;
import mcimages.block.IceBlock;
import mcimages.block.SideBlock;
import mcimages.block.TopBlock;
import mcimages.block.TopBottomBlock;
import mcimages.block.TrapdoorBlock;
import mcimages.context.Context;
import mcimages.context.DirectionalContext;
import mcimages.structure.Colorspace;
import mcimages.structure.Structure;
import mcimages.structure.SkinStructure;
import x590.util.Timer;
import x590.util.Util;

import static mcimages.Versions.*;

public class McImages {
	
	public static final List<Entry<Integer, Block>> blocks = createBlockMap();
	
	
	private static List<Entry<Integer, Block>> createBlockMap() {
		final List<Entry<Integer, Block>> blocks = new ArrayList<>(246);
		
		blocks.add(Map.entry(0x807F7F, new Block("cobblestone")));
		blocks.add(Map.entry(0x6D765E, new Block("mossy_cobblestone")));
		
		blocks.add(Map.entry(0x7E7E7E, new Block("stone")));
		blocks.add(Map.entry(0xA1A1A1, new Block("smooth_stone")));
		blocks.add(Map.entry(0xA8A8A8, new SideBlock("smooth_stone_slab[type=double]")));
		
		blocks.add(Map.entry(0x888889, new Block("andesite")));
		blocks.add(Map.entry(0x848786, new Block("polished_andesite")));
		blocks.add(Map.entry(0x956756, new Block("granite")));
		blocks.add(Map.entry(0x9B6B59, new Block("polished_granite")));
		blocks.add(Map.entry(0xBDBDBD, new Block("diorite")));
		blocks.add(Map.entry(0xC3C3C5, new Block("polished_diorite")));
		
		blocks.add(Map.entry(0x7A7A7A, new Block("stone_bricks")));
		blocks.add(Map.entry(0x74796A, new Block("mossy_stone_bricks")));
		blocks.add(Map.entry(0x767676, new Block("cracked_stone_bricks")));
		blocks.add(Map.entry(0x787778, new Block("chiseled_stone_bricks")));
		
		blocks.add(Map.entry(0xD8CA9A, new SideBlock("sandstone")));
		blocks.add(Map.entry(0xD8CB9C, new BottomBlock("sandstone")));
		blocks.add(Map.entry(0xDACFA0, new SideBlock("cut_sandstone")));
		blocks.add(Map.entry(0xD8CB9B, new SideBlock("chiseled_sandstone")));
		blocks.add(Map.entry(0xE0D6AA, new TopBlock("smooth_sandstone")));
		
		blocks.add(Map.entry(0xBB631D, new SideBlock("red_sandstone")));
		blocks.add(Map.entry(0xBA631C, new BottomBlock("red_sandstone")));
		blocks.add(Map.entry(0xBE6620, new Block("cut_red_sandstone")));
		blocks.add(Map.entry(0xB7601B, new Block("chiseled_red_sandstone")));
		blocks.add(Map.entry(0xB5621F, new TopBlock("smooth_red_sandstone")));
		
		blocks.add(Map.entry(0x0F0B19, new Block("obsidian")));
		blocks.add(Map.entry(0x565656, new Block("bedrock")));
		
		
		blocks.add(Map.entry(0x622727, new Block("netherrack")));
		blocks.add(Map.entry(0x2C161A, new Block("nether_bricks")));
		blocks.add(Map.entry(0x460709, new Block("red_nether_bricks")));
		blocks.add(Map.entry(0x30181C, new Block("chiseled_nether_bricks")));
		blocks.add(Map.entry(0x281418, new Block("cracked_nether_bricks")));
		
		blocks.add(Map.entry(0x8E3F20, new Block("magma_block")));
		blocks.add(Map.entry(0x523E33, new Block("soul_sand")));
		blocks.add(Map.entry(0x101010, new Block("coal_block")));
		blocks.add(Map.entry(0xDEDEDE, new Block("iron_block")));
		blocks.add(Map.entry(0xF8D33E, new Block("gold_block")));
		blocks.add(Map.entry(0x2BCD5A, new Block("emerald_block")));
		blocks.add(Map.entry(0x1F438C, new Block("lapis_block")));
		blocks.add(Map.entry(0x65EFE5, new Block("diamond_block")));
		blocks.add(Map.entry(0x639C97, new Block("prismarine")));
		blocks.add(Map.entry(0x63AC9F, new Block("prismarine_bricks")));
		blocks.add(Map.entry(0x345C4C, new Block("dark_prismarine")));
		
		blocks.add(Map.entry(0xB1CBC2, new ConditionalBlock("sea_lantern", context -> context.useSeaLantern())));
		
		
		blocks.add(Map.entry(0xA91705, new Block("redstone_block")));
		blocks.add(Map.entry(0x64391F, new Block("redstone_lamp")));
		
		
		blocks.add(Map.entry(0x6D901E, new TopBottomBlock("melon")));
		blocks.add(Map.entry(0x72921E, new SideBlock("melon")));
		blocks.add(Map.entry(0x6FC05B, new Block("slime_block")));
		blocks.add(Map.entry(0xC4C14B, new Block("sponge")));
		blocks.add(Map.entry(0xAAB446, new Block("wet_sponge")));
		
		blocks.add(Map.entry(0xECE6DF, new TopBlock("quartz_block")));
		blocks.add(Map.entry(0xECE6DF, new SideBlock("quartz_block")));
		blocks.add(Map.entry(0xEDE6E0, new BottomBlock("quartz_block")));
		blocks.add(Map.entry(0xEBE5DE, new Block("quartz_bricks")));
		blocks.add(Map.entry(0xECE7E0, new ColumnSideBlock("quartz_pillar")));
		blocks.add(Map.entry(0xECE6E0, new ColumnEndBlock("quartz_pillar")));
		blocks.add(Map.entry(0xE8E3DA, new TopBottomBlock("chiseled_quartz_block")));
		blocks.add(Map.entry(0xE8E3DA, new SideBlock("chiseled_quartz_block")));
		
		blocks.add(Map.entry(0xAA7EAA, new Block("purpur_block")));
		blocks.add(Map.entry(0xAC81AC, new ColumnSideBlock("purpur_pillar")));
		blocks.add(Map.entry(0xAB7FAB, new ColumnEndBlock("purpur_pillar")));
		
		blocks.add(Map.entry(0xE6E3D1, new ColumnSideBlock("bone_block")));
		blocks.add(Map.entry(0xD0CCB1, new ColumnEndBlock("bone_block")));
		
		blocks.add(Map.entry(0x080A0F, new Block("black_concrete")));
		blocks.add(Map.entry(0x157788, new Block("cyan_concrete")));
		blocks.add(Map.entry(0x2489C7, new Block("light_blue_concrete")));
		blocks.add(Map.entry(0x2D2F8F, new Block("blue_concrete")));
		blocks.add(Map.entry(0x373A3E, new Block("gray_concrete")));
		blocks.add(Map.entry(0x495B24, new Block("green_concrete")));
		blocks.add(Map.entry(0x5EA919, new Block("lime_concrete")));
		blocks.add(Map.entry(0x603C20, new Block("brown_concrete")));
		blocks.add(Map.entry(0x64209C, new Block("purple_concrete")));
		blocks.add(Map.entry(0x7D7D73, new Block("light_gray_concrete")));
		blocks.add(Map.entry(0x8E2121, new Block("red_concrete")));
		blocks.add(Map.entry(0xA9309F, new Block("magenta_concrete")));
		blocks.add(Map.entry(0xCFD5D6, new Block("white_concrete")));
		blocks.add(Map.entry(0xD6658F, new Block("pink_concrete")));
		blocks.add(Map.entry(0xE06101, new Block("orange_concrete")));
		blocks.add(Map.entry(0xF1AF15, new Block("yellow_concrete")));
		
		// Превращаются в мёртвые кораллы без воды
//		blocks.add(Map.entry(0x3158CF, new Block("tube_coral_block")));
//		blocks.add(Map.entry(0xA4232F, new Block("fire_coral_block")));
//		blocks.add(Map.entry(0xA61BA3, new Block("bubble_coral_block")));
//		blocks.add(Map.entry(0xD8C742, new Block("horn_coral_block")));
		
		blocks.add(Map.entry(0x866043, new Block("dirt")));
		blocks.add(Map.entry(0xA1A6B3, new Block("clay")));
//		blocks.add(Map.entry(0x949494, new TopBlock("grass_block")));
		blocks.add(Map.entry(0x5C3F18, new TopBlock("podzol")));
		blocks.add(Map.entry(0x705647, new TopBlock("mycelium")));
		blocks.add(Map.entry(0x77553B, new Block("coarse_dirt")));
		blocks.add(Map.entry(0x947A41, new TopBlock("dirt_path")));
		
		blocks.add(Map.entry(0xF9FEFE, new Block("snow_block")));
		blocks.add(Map.entry(0x91B8FE, new IceBlock("ice")));
		blocks.add(Map.entry(0x8DB4FA, new IceBlock("packed_ice")));
		
		
		blocks.add(Map.entry(0xC0AF79, new Block("birch_planks")));
		blocks.add(Map.entry(0x432B14, new Block("dark_oak_planks")));
		blocks.add(Map.entry(0x735531, new Block("spruce_planks")));
		blocks.add(Map.entry(0xA17351, new Block("jungle_planks")));
		blocks.add(Map.entry(0xA2834F, new Block("oak_planks")));
		blocks.add(Map.entry(0xA85A32, new Block("acacia_planks")));
		blocks.add(Map.entry(0xDBDF9E, new Block("end_stone")));
		blocks.add(Map.entry(0xDBE0A2, new Block("end_stone_bricks")));
		
		blocks.add(Map.entry(0x985E44, new Block("terracotta")));
		blocks.add(Map.entry(0x251710, new Block("black_terracotta")));
		blocks.add(Map.entry(0x3A2A24, new Block("gray_terracotta")));
		blocks.add(Map.entry(0x4A3C5B, new Block("blue_terracotta")));
		blocks.add(Map.entry(0x4C532A, new Block("green_terracotta")));
		blocks.add(Map.entry(0x4D3324, new Block("brown_terracotta")));
		blocks.add(Map.entry(0x575B5B, new Block("cyan_terracotta")));
		blocks.add(Map.entry(0x687635, new Block("lime_terracotta")));
		blocks.add(Map.entry(0x716D8A, new Block("light_blue_terracotta")));
		blocks.add(Map.entry(0x764656, new Block("purple_terracotta")));
		blocks.add(Map.entry(0x876B62, new Block("light_gray_terracotta")));
		blocks.add(Map.entry(0x8F3D2F, new Block("red_terracotta")));
		blocks.add(Map.entry(0x96586D, new Block("magenta_terracotta")));
		blocks.add(Map.entry(0xA24E4F, new Block("pink_terracotta")));
		blocks.add(Map.entry(0xA25426, new Block("orange_terracotta")));
		blocks.add(Map.entry(0xBA8523, new Block("yellow_terracotta")));
		blocks.add(Map.entry(0xD2B2A1, new Block("white_terracotta")));
		
		blocks.add(Map.entry(0x15151A, new Block("black_wool")));
		blocks.add(Map.entry(0x158A91, new Block("cyan_wool")));
		blocks.add(Map.entry(0x35399D, new Block("blue_wool")));
		blocks.add(Map.entry(0x3AAFD9, new Block("light_blue_wool")));
		blocks.add(Map.entry(0x3F4448, new Block("gray_wool")));
		blocks.add(Map.entry(0x556E1B, new Block("green_wool")));
		blocks.add(Map.entry(0x70B91A, new Block("lime_wool")));
		blocks.add(Map.entry(0x724829, new Block("brown_wool")));
		blocks.add(Map.entry(0x7A2AAC, new Block("purple_wool")));
		blocks.add(Map.entry(0x8E8E87, new Block("light_gray_wool")));
		blocks.add(Map.entry(0xA12723, new Block("red_wool")));
		blocks.add(Map.entry(0xBE45B4, new Block("magenta_wool")));
		blocks.add(Map.entry(0xEAECED, new Block("white_wool")));
		blocks.add(Map.entry(0xEE8DAC, new Block("pink_wool")));
		blocks.add(Map.entry(0xF17613, new Block("orange_wool")));
		blocks.add(Map.entry(0xF9C628, new Block("yellow_wool")));
		
//		blocks.add(Map.entry(0xDBDAD5, new ColumnSideBlock("birch_wood")));
		blocks.add(Map.entry(0x3C2F1A, new ColumnSideBlock("dark_oak_wood")));
		blocks.add(Map.entry(0x3B2611, new ColumnSideBlock("spruce_wood")));
		blocks.add(Map.entry(0x554419, new ColumnSideBlock("jungle_wood")));
		blocks.add(Map.entry(0x676157, new ColumnSideBlock("acacia_wood")));
		blocks.add(Map.entry(0x6D5533, new ColumnSideBlock("oak_wood")));
		blocks.add(Map.entry(0x442D16, new ColumnEndBlock("dark_oak_wood")));
		blocks.add(Map.entry(0x6E5230, new ColumnEndBlock("spruce_wood")));
		blocks.add(Map.entry(0x9B7D4B, new ColumnEndBlock("oak_wood")));
		blocks.add(Map.entry(0x84807F, new FallingBlock("gravel")));
		blocks.add(Map.entry(0xDBCFA3, new FallingBlock("sand")));
		blocks.add(Map.entry(0xBE6721, new FallingBlock("red_sand")));
		blocks.add(Map.entry(0x191B20, new FallingBlock("black_concrete_powder")));
		blocks.add(Map.entry(0x25949D, new FallingBlock("cyan_concrete_powder")));
		blocks.add(Map.entry(0x4649A7, new FallingBlock("blue_concrete_powder")));
		blocks.add(Map.entry(0x4AB5D5, new FallingBlock("light_blue_concrete_powder")));
		blocks.add(Map.entry(0x4D5155, new FallingBlock("gray_concrete_powder")));
		blocks.add(Map.entry(0x61772D, new FallingBlock("green_concrete_powder")));
		blocks.add(Map.entry(0x7E5536, new FallingBlock("brown_concrete_powder")));
		blocks.add(Map.entry(0x7EBD2A, new FallingBlock("lime_concrete_powder")));
		blocks.add(Map.entry(0x8438B2, new FallingBlock("purple_concrete_powder")));
		blocks.add(Map.entry(0x9B9B94, new FallingBlock("light_gray_concrete_powder")));
		blocks.add(Map.entry(0xA83633, new FallingBlock("red_concrete_powder")));
		blocks.add(Map.entry(0xC154B9, new FallingBlock("magenta_concrete_powder")));
		blocks.add(Map.entry(0xE2E4E4, new FallingBlock("white_concrete_powder")));
		blocks.add(Map.entry(0xE38420, new FallingBlock("orange_concrete_powder")));
		blocks.add(Map.entry(0xE59AB5, new FallingBlock("pink_concrete_powder")));
		blocks.add(Map.entry(0xE9C737, new FallingBlock("yellow_concrete_powder")));
		
		blocks.add(Map.entry(0xCBC4B9, new Block("mushroom_stem")));
		blocks.add(Map.entry(0x957051, new Block("brown_mushroom_block")));
		blocks.add(Map.entry(0xCAAA78, new BlockInside("brown_mushroom_block")));
		
		
		blocks.add(Map.entry(0xC6B177, new ColumnSideBlock("stripped_birch_wood",    V1_13)));
		blocks.add(Map.entry(0x493924, new ColumnSideBlock("stripped_dark_oak_wood", V1_13)));
		blocks.add(Map.entry(0x745A35, new ColumnSideBlock("stripped_spruce_wood",   V1_13)));
		blocks.add(Map.entry(0xAC8555, new ColumnSideBlock("stripped_jungle_wood",   V1_13)));
		blocks.add(Map.entry(0xB05D3C, new ColumnSideBlock("stripped_acacia_wood",   V1_13)));
		blocks.add(Map.entry(0xB39157, new ColumnSideBlock("stripped_oak_wood",      V1_13)));
		blocks.add(Map.entry(0xC0AD76, new ColumnEndBlock("stripped_birch_wood",    V1_13)));
		blocks.add(Map.entry(0x432C16, new ColumnEndBlock("stripped_dark_oak_wood", V1_13)));
		blocks.add(Map.entry(0x6C5230, new ColumnEndBlock("stripped_spruce_wood",   V1_13)));
		blocks.add(Map.entry(0xA67A52, new ColumnEndBlock("stripped_jungle_wood",   V1_13)));
		blocks.add(Map.entry(0xA75B33, new ColumnEndBlock("stripped_acacia_wood",   V1_13)));
		blocks.add(Map.entry(0xA2834E, new ColumnEndBlock("stripped_oak_wood",      V1_13)));
		blocks.add(Map.entry(0x4B3217, new TrapdoorBlock("dark_oak_trapdoor", V1_13)));
		blocks.add(Map.entry(0x684F30, new TrapdoorBlock("spruce_trapdoor",   V1_13)));
		
		blocks.add(Map.entry(0x7D7673, new Block("dead_brain_coral_block",  V1_13)));
		blocks.add(Map.entry(0x837C78, new Block("dead_tube_coral_block",   V1_13)));
		blocks.add(Map.entry(0x847C78, new Block("dead_bubble_coral_block", V1_13)));
//		blocks.add(Map.entry(0x847C78, new Block("dead_fire_coral_block",   V1_13))); // duplicate
		blocks.add(Map.entry(0x857E7A, new Block("dead_horn_coral_block",   V1_13)));
		
		blocks.add(Map.entry(0x74A8FD, new IceBlock("blue_ice", V1_13)));
		
		blocks.add(Map.entry(0xC57618, new TopBottomBlock("pumpkin", V1_13)));
		blocks.add(Map.entry(0xC67519, new      SideBlock("pumpkin", V1_13)));
		
		
		blocks.add(Map.entry(0xA68C0C, new ColumnEndBlock("hay_block", V1_15)));
		blocks.add(Map.entry(0xFBB934, new    TopBlock("honey_block", V1_15)));
		blocks.add(Map.entry(0xFBBC39, new   SideBlock("honey_block", V1_15)));
		blocks.add(Map.entry(0xF08F11, new BottomBlock("honey_block", V1_15)));
		blocks.add(Map.entry(0xE5951E, new Block("honeycomb_block",   V1_15)));
		
		
		blocks.add(Map.entry(0x653147, new Block("crimson_planks", V1_16)));
		blocks.add(Map.entry(0x2B6963, new Block("warped_planks",  V1_16)));
		
		blocks.add(Map.entry(0x5D1A1E, new ColumnSideBlock("crimson_stem", V1_16)));
		blocks.add(Map.entry(0x723349, new ColumnEndBlock("crimson_stem", V1_16)));
		blocks.add(Map.entry(0x3A3B4E, new ColumnSideBlock("warped_stem",  V1_16)));
		
		blocks.add(Map.entry(0x8A3A5B, new ColumnSideBlock("stripped_crimson_stem", V1_16)));
		blocks.add(Map.entry(0x793852, new  ColumnEndBlock("stripped_crimson_stem", V1_16)));
		blocks.add(Map.entry(0x3A9895, new ColumnSideBlock("stripped_warped_stem",  V1_16)));
		blocks.add(Map.entry(0x347F7B, new  ColumnEndBlock("stripped_warped_stem",  V1_16)));
		
		blocks.add(Map.entry(0x2B7365, new TopBlock("warped_nylium",  V1_16)));
		blocks.add(Map.entry(0x832020, new TopBlock("crimson_nylium", V1_16)));
		
		blocks.add(Map.entry(0x177879, new Block("warped_wart_block", V1_16)));
		blocks.add(Map.entry(0x730302, new Block("nether_wart_block", V1_16)));
		
		blocks.add(Map.entry(0x515156, new  TopBlock("basalt", V1_16)));
		blocks.add(Map.entry(0x4A494F, new SideBlock("basalt", V1_16)));
		blocks.add(Map.entry(0x656466, new  TopBlock("polished_basalt", V1_16)));
		blocks.add(Map.entry(0x5B5B5E, new SideBlock("polished_basalt", V1_16)));
		blocks.add(Map.entry(0x48484E, new Block("smooth_basalt", V1_16)));
		
		blocks.add(Map.entry(0x2A2329, new      SideBlock("blackstone", V1_16)));
		blocks.add(Map.entry(0x2A242A, new TopBottomBlock("blackstone", V1_16)));
		blocks.add(Map.entry(0x353139, new Block("polished_blackstone",          V1_16)));
		blocks.add(Map.entry(0x363139, new Block("chiseled_polished_blackstone", V1_16)));
		blocks.add(Map.entry(0x302B32, new Block("polished_blackstone_bricks",         V1_16)));
		blocks.add(Map.entry(0x2C262C, new Block("cracked_polished_blackstone_bricks", V1_16)));
		
		blocks.add(Map.entry(0xF2974C, new Block("shroomlight", V1_16)));
		
		blocks.add(Map.entry(0x4C3A2F, new Block("soul_soil", V1_16)));
		
		blocks.add(Map.entry(0x604038, new      SideBlock("ancient_debris", V1_16)));
		blocks.add(Map.entry(0x5E4139, new TopBottomBlock("ancient_debris", V1_16)));
		blocks.add(Map.entry(0x443F41, new Block("netherite_block", V1_16)));
		
		blocks.add(Map.entry(0x220A3F, new Block("crying_obsidian", V1_16)));
		
		blocks.add(Map.entry(0x757578, new      SideBlock("lodestone", V1_16)));
		blocks.add(Map.entry(0x929397, new TopBottomBlock("lodestone", V1_16)));
		
		
		blocks.add(Map.entry(0x505052, new ColumnSideBlock("deepslate", V1_17)));
		blocks.add(Map.entry(0x575759, new  ColumnEndBlock("deepslate", V1_17)));
		blocks.add(Map.entry(0x474747, new Block("deepslate_bricks",         V1_17)));
		blocks.add(Map.entry(0x414041, new Block("cracked_deepslate_bricks", V1_17)));
//		blocks.add(Map.entry(0x373738, new Block("chiseled_deepslate", V1_17))); // duplicate
		blocks.add(Map.entry(0x484849, new Block("polished_deepslate", V1_17)));
		blocks.add(Map.entry(0x4D4D50, new Block("cobbled_deepslate",  V1_17)));
		blocks.add(Map.entry(0x373738, new Block("deepslate_tiles",         V1_17)));
		blocks.add(Map.entry(0x353535, new Block("cracked_deepslate_tiles", V1_17)));
		
		blocks.add(Map.entry(0x6C6D67, new Block("tuff", V1_17)));
		blocks.add(Map.entry(0xE0E1DD, new Block("calcite", V1_17)));
		blocks.add(Map.entry(0x866B5C, new Block("dripstone_block", V1_17)));
		blocks.add(Map.entry(0x8560BA, new ConditionalBlock("budding_amethyst", V1_17, context -> context.useBuddingAmethyst())));
		blocks.add(Map.entry(0x8662BF, new Block("amethyst_block", V1_17)));
		
		blocks.add(Map.entry(0x596E2D, new Block("moss_block", V1_17)));
		
		blocks.add(Map.entry(0x90674C, new Block("rooted_dirt", V1_17)));
		blocks.add(Map.entry(0xF8FDFD, new Block("powder_snow", V1_17)));
		
		blocks.add(Map.entry(0xC06C50, new Block("waxed_copper_block",     V1_17)));
		blocks.add(Map.entry(0xA17E68, new Block("waxed_exposed_copper",   V1_17)));
		blocks.add(Map.entry(0x6C9A6F, new Block("waxed_weathered_copper", V1_17)));
		blocks.add(Map.entry(0x53A486, new Block("waxed_oxidized_copper",  V1_17)));
		blocks.add(Map.entry(0xBF6B51, new Block("waxed_cut_copper",           V1_17)));
		blocks.add(Map.entry(0x9B7A65, new Block("waxed_exposed_cut_copper",   V1_17)));
		blocks.add(Map.entry(0x6D916B, new Block("waxed_weathered_cut_copper", V1_17)));
		blocks.add(Map.entry(0x509A7F, new Block("waxed_oxidized_cut_copper",  V1_17)));
		
		
		blocks.add(Map.entry(0x544329, new ColumnSideBlock("mangrove_wood", V1_19)));
		blocks.add(Map.entry(0x682F2A, new  ColumnEndBlock("mangrove_wood", V1_19)));
		blocks.add(Map.entry(0x763631, new Block("mangrove_planks", V1_19)));
		
		return Collections.unmodifiableList(blocks);
	}
	
	private static final Pattern VERSION_PATTERN = Pattern.compile("^1\\.(\\d+)(?:\\.(\\d+))?$");
	
	private static StructureType structureType = StructureType.IMAGE;
	
	
	private static final String pathSeparator = System.getProperty("file.separator");
	
	
	public static void main(String[] args) throws Exception {
		
		ArgsNamespace arguments = new StandartArgParser("McImages")
				.add(new StringOption("images").help("Images to be processed").times(Times.ONE_OR_MORE))
				.add(new StringOption("-o", "--output").help("Output files").multiargs())
				
				.add(new IntOption("-w", "--width").min(0).defaultValue(-1)
						.help("Structure width"))
				.add(new IntOption("-h", "--height").min(0).defaultValue(-1)
						.help("Structure height. Cannot be greater then 256 (320 for 1.18+) by vertical"))
				
				.add(new IntOption("-v", "--version").min(0).defaultValue(V1_18_2).parser(value -> {
						if(Pattern.matches("\\d+", value)) {
							return Integer.parseInt(value);
						}
						
						Matcher matcher = VERSION_PATTERN.matcher(value);
						if(matcher.find()) {
							int version = Integer.parseInt(matcher.group(1));
							
							String minor = matcher.group(2);
							
							if(minor != null)
								version += Integer.parseInt(minor);
							
							return version;
						}
						
						throw new ArgumentParseException(value);
						
					}).help("Minecraft version. By default is 1.18.2"))
				
				.add(new EnumOption<Direction>(Direction.class, "-d", "--direction").help("Direction of building. Default is north")
						.defaultValue(Direction.NORTH))
				
				.add(new Flag("-i", "--image").help("Generate image structure").action(namespace -> structureType = StructureType.IMAGE))
				.add(new Flag("-b", "--block").help("Generate block structure").action(namespace -> structureType = StructureType.BLOCK))
				.add(new Flag("-s", "--skin") .help("Generate skin structure") .action(namespace -> structureType = StructureType.SKIN))
				
				.add(new StringOption("-D", "--datapack").help("Generate datapack").implicitValue("mcimages").weak())
				
				.add(new EnumOption<>(Colorspace.class, "-c", "--colorspace").help("Use specified colorspace to quantise image")
						.defaultValue(Colorspace.RGB))
				
				.add(new Flag("--rgb").help("Use RGB colorspace").action(namespace -> namespace.set("--colorspace", Colorspace.RGB)))
				.add(new Flag("--xyz").help("Use XYZ colorspace").action(namespace -> namespace.set("--colorspace", Colorspace.XYZ)))
				.add(new Flag("--lab").help("Use Lab colorspace").action(namespace -> namespace.set("--colorspace", Colorspace.LAB)))
				.add(new Flag("--ycbcr").help("Use YCbCr colorspace").action(namespace -> namespace.set("--colorspace", Colorspace.YCbCr)))
				
				.add(new Flag("--ice").help("Use ice, packed ice and blue ice blocks"))
				.add(new Flag("--amethyst", "--budding-amethyst").help("Use budding amethyst blocks"))
				.add(new Flag("--sea-lantern").help("Use sea lantern"))
				.parse(args);
		
		int width = arguments.getInt("--width"),
			height = arguments.getInt("--height");
		
		int mcVersion = arguments.getInt("--version");
		Direction direction = arguments.get("--direction");
		
		
		List<String> inputPaths = arguments.getAll("images");
		List<String> outputPaths = arguments.getAll("--output");
		
		ZipOutputStream zipOut;
		
		String datapackNamespace = arguments.getString("--datapack");
		
		if(datapackNamespace != null && outputPaths.size() == 1) {
			
			File outputFile = new File(outputPaths.get(0));
			if(outputFile.isDirectory())
				outputFile = Paths.get(outputFile.getPath(), datapackNamespace + ".zip").toFile();
			
			zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
			
			zipOut.putNextEntry(new ZipEntry("pack.mcmeta"));
			zipOut.write(("{\"pack\":{\"pack_format\":" + Versions.packFormatByVersion(mcVersion) + ",\"description\":\"Auto generated datapack which contains functions to build images\"}}").getBytes());
			zipOut.closeEntry();
			
			zipOut.putNextEntry(new ZipEntry("data" + pathSeparator + "minecraft" + pathSeparator + "tags" + pathSeparator + "functions" + pathSeparator + "load.json"));
			zipOut.write(("{\"values\":[\"" + datapackNamespace + ":load\"]}").getBytes());
			zipOut.closeEntry();
			
			zipOut.putNextEntry(new ZipEntry("data" + pathSeparator + datapackNamespace + pathSeparator + "functions" + pathSeparator + "load.mcfunction"));
			zipOut.write(("say Datapack '" + datapackNamespace + "' loaded").getBytes());
			zipOut.closeEntry();
			
		} else {
			zipOut = null;
		}
		
		
		final int maxVerticalHeight = mcVersion < V1_18 ? 256 : 320;
		
		
		if(direction.isHorisontal() && height > maxVerticalHeight) {
			System.err.println("Height is too high, it cannot be more than " + maxVerticalHeight);
			System.exit(1);
		}
		
		
		System.out.println("Blocks size = " + blocks.size());
		
		DirectionalContext context = new DirectionalContext(arguments, structureType);
		
		
		Iterator<String> outputPathIter = outputPaths.iterator();
		
		
		Set<String> writedFunctions = new HashSet<>(inputPaths.size());
		
		
		for(String inputPath : inputPaths) {
			
			BufferedImage image = readImage(inputPath, outputPathIter);
			
			if(image == null) continue;
			
			image = resizeIfNecessary(image, context, inputPath, width, height, maxVerticalHeight);
			
			if(image == null) continue;
			
			System.out.println("Image size = " + image.getWidth() + "x" + image.getHeight());
			
			
			String outputPath = outputPathIter.hasNext() ? outputPathIter.next() : inputPath.replaceFirst("\\.\\w+$", "") + ".mcfunction";
			
			OutputStream out = null;
			
			try {
				if(zipOut != null) {
					out = zipOut;
					
					String mcFunctionName = 
							Paths.get(inputPath).getFileName().toString().replaceFirst("\\.\\w+$", "").toLowerCase().replace("[ !\"#$%&'()*+,.;<=>?@\\[\\\\\\]^`{|}~]", "-");
					
					if(writedFunctions.contains(mcFunctionName)) {
						String originalMcFunctionName = mcFunctionName;
						mcFunctionName = originalMcFunctionName + "-2";
						
						for(int i = 3; writedFunctions.contains(mcFunctionName); i++) {
							mcFunctionName = originalMcFunctionName + "-" + i;
						}
					}
					
					writedFunctions.add(mcFunctionName);
					
					zipOut.putNextEntry(new ZipEntry("data" + pathSeparator + datapackNamespace + pathSeparator +
							"functions" + pathSeparator + mcFunctionName + ".mcfunction"));
					
				} else {
					out = new BufferedOutputStream(new FileOutputStream(outputPath));
				}
				
				OutputStreamWriter outWriter = new OutputStreamWriter(out);
				
				long start = System.currentTimeMillis();
				
				Structure structure = context.createStructure(image);
				
				System.out.println("Converted in " + (System.currentTimeMillis() - start) + " ms");
				
				
				Timer writingTimer = Timer.startNewTimer();
				
				structure.writeTo(outWriter);
				
				outWriter.flush();
				
				writingTimer.logElapsed("Output writing");
				
				
				if(zipOut != null) {
					zipOut.closeEntry();
				}
				
				
				int minRenderDistance = structure.minRenderDistance();
				if(minRenderDistance > 2)
					System.out.println("Minimal render distance: " + minRenderDistance + " chunks");
				
			} catch(IOException ex) {
				System.err.println(ex);
				continue;
			} finally {
				if(out != null && out != zipOut)
					out.close();
			}
		}
		
		if(zipOut != null)
			zipOut.close();
	}
	
	
	private static BufferedImage readImage(String inputPath, Iterator<String> outputPathIter) {
		try {
			BufferedImage image = ImageIO.read(new File(inputPath));
			
			if(image == null) {
				if(outputPathIter.hasNext())
					outputPathIter.next();
				
				System.err.println("Cannot read image \"" + inputPath + "\"");
			}
			
			return image;
			
		} catch(IOException ex) {
			if(outputPathIter.hasNext())
				outputPathIter.next();
			
			System.err.println(ex);
			return null;
		}
	}
	
	
	private static BufferedImage resizeIfNecessary(BufferedImage image, Context context, String imagePath, int width, int height, int maxVerticalHeight) {
		
		int imgWidth = image.getWidth(),
			imgHeight = image.getHeight();
		
		boolean shouldResize = false;
		
		if(context.getStructureType() == StructureType.BLOCK && imgWidth != imgHeight) {
			if(Util.askUser("Block texture \"" + imagePath + "\" is not square. Resize to square? (Y/n) ")) {
				imgWidth = imgHeight = Math.min(imgWidth, imgHeight);
				shouldResize = true;
			} else {
				return null;
			}
			
		} else if(context.getStructureType() == StructureType.SKIN &&
				(imgWidth != imgHeight || imgWidth % SkinStructure.IMAGE_SIDE_SIZE != 0 || imgHeight % SkinStructure.IMAGE_SIDE_SIZE != 0)) {
			System.err.println("Skin texture \"" + imagePath + "\" must be square and the sides must be divided by 64 completely");
			return null;
		}
		
		
		double scale = Math.min(width > 0 && imgWidth > width ? (double)width / imgWidth : 1,
				(double)(height > 0 && imgHeight > height ? height : maxVerticalHeight) / imgHeight);
		
		if(shouldResize || scale != 1) {
			imgWidth = (int)Math.round(imgWidth * scale);
			imgHeight = (int)Math.round(imgHeight * scale);
			
			return Util.resizeImage(image, imgWidth, imgHeight);
		}
		
		return image;
	}
}