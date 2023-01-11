package mcimages.structure;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import mcimages.McImages;
import mcimages.block.Block;
import mcimages.context.Context;
import mcimages.context.DirectionalContext;

import x590.util.Timer;

public class ImageStructure extends Structure {
	
	public static final int MAX_REGION_SIZE = 32768;
	public static final int CHUNK_WIDTH = 16;
	
	private final int offsetRX, offsetRY, offsetRZ;
	private final DirectionalContext context;
	private final int minRenderDistance;
	private final List<Region> regions;
	
	
	private static long start = Long.MAX_VALUE, end = 0;
	
	
	private static void fillPalette(Context context, BufferedImage image, List<Entry<Integer, Block>> blocks,
			int startX, int startY, int endX, int endY, Map<Integer, Block> palette) {
		
		var colorspace = context.getColorspace();
		
		start = Math.min(start, System.currentTimeMillis());
		
		for(int x = startX; x < endX; x++) {
			for(int y = startY; y < endY; y++) {
				int rgb = image.getRGB(x, y) & 0xFFFFFF;
				
				// Два отдельных блока synchronized нужны,
				// чтобы метод Colorspace#getBlockForColor
				// вызывался между ними
				
				synchronized(palette) {
					if(palette.containsKey(rgb)) {
						continue;
					}
					
					// Заполняем ячейку чем-то, чтобы другой
					// поток не вычислял тот же самый цвет
					palette.put(rgb, null);
				}
				
				Block block = colorspace.getBlockForColor(rgb, blocks);
				
				// Окончательно помещаем цвет в палитру
				synchronized(palette) {
					palette.put(rgb, block);
				}
			}
		}
		
		end = Math.max(end, System.currentTimeMillis());
	}
	
	private static void fillPaletteSingle(Context context, BufferedImage image, List<Entry<Integer, Block>> blocks,
			int startX, int startY, int endX, int endY, Map<Integer, Block> palette) {
		
		var colorspace = context.getColorspace();
		
		start = Math.min(start, System.currentTimeMillis());
		
		for(int x = startX; x < endX; x++) {
			for(int y = startY; y < endY; y++) {
				int rgb = image.getRGB(x, y) & 0xFFFFFF;
				if(!palette.containsKey(rgb)) {
					palette.put(rgb, colorspace.getBlockForColor(rgb, blocks));
				}
			}
		}
		
		end = Math.max(end, System.currentTimeMillis());
	}
	
	
	private static List<Entry<Integer, Block>> filterBlocks(Context context) {
		return McImages.blocks.stream()
				.filter(entry -> entry.getValue().canUse(context))
				.collect(Collectors.toList());
	}
	
	
	public static Map<Integer, Block> generatePalette(Context context, BufferedImage image) {
		return generatePalette(context, image, filterBlocks(context), 0, 0, image.getWidth(), image.getHeight());
	}
	
	public static Map<Integer, Block> generatePalette(Context context, BufferedImage image,
			int startX, int startY, int endX, int endY) {
		return generatePalette(context, image, filterBlocks(context), startX, startY, endX, endY);
	}
	
	private static Map<Integer, Block> generatePalette(Context context, BufferedImage image, List<Entry<Integer, Block>> blocks,
			int startX, int startY, int endX, int endY) {
		
		int width = endX - startX,
			height = endY - startY;
		
		Map<Integer, Block> palette = new HashMap<>(image.getWidth() * image.getHeight() / 2); // Примерно
		
		
		Timer timer = Timer.startNewTimer();
		
		
		final int availableProcessors = Runtime.getRuntime().availableProcessors();
		
		if(availableProcessors > 1) {
			
			int minOfSizes = Math.min(width, height);
			
			int threadsCount = Math.min(minOfSizes, availableProcessors);
			
			System.out.println(threadsCount + " threads");
			
			List<Thread> threads = new ArrayList<>(threadsCount - 1);
			
			Runnable executor;
			
			
			float step = minOfSizes / (float)threadsCount;
			
			if(minOfSizes == width) {
				for(float x = startX;;) {
					float   currX = x,
							nextX = x + step;
					
					executor = () -> fillPalette(context, image, blocks, (int)currX, startY, (int)nextX, endY, palette);
					x = nextX;
					
					if(x < endX)
						threads.add(new Thread(executor));
					else
						break;
				}
				
			} else {
				for(float y = startY;;) {
					float   currY = y,
							nextY = y + step;
					
					executor = () -> fillPalette(context, image, blocks, startX, (int)currY, endX, (int)nextY, palette);
					y = nextY;
					
					if(y < endY)
						threads.add(new Thread(executor));
					else
						break;
				}
			}
			
			
			threads.forEach(Thread::start);
			
			executor.run();
			
			threads.forEach(thread -> {
				try {
					thread.join();
				} catch(InterruptedException ex) {
					ex.printStackTrace();
					System.exit(-1);
				}
			});
			
		} else {
			System.out.println("Single thread");
			fillPaletteSingle(context, image, blocks, 0, 0, width, height, palette);
		}
		
		System.out.println("Palette (clear time): " + (end - start) + " ms elapsed");
		timer.logElapsed("Palette");
		
		return palette;
	}
	
	
	private Map<Integer, String> paletteFromBlockPalette(Map<Integer, Block> blockPalette) {
		return blockPalette.entrySet().stream()
				.map(entry -> Map.entry(entry.getKey(), entry.getValue().getPlaceState(context.getDirection())))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}
	
	
	public ImageStructure(DirectionalContext context, BufferedImage image) {
		this(context, image, 0, 0, 0);
	}
	
	public ImageStructure(DirectionalContext context, BufferedImage image,
			int offsetRX, int offsetRY, int offsetRZ) {
		
		this(context, image, offsetRX, offsetRY, offsetRZ, 0, 0, image.getWidth(), image.getHeight());
	}
	
	public ImageStructure(DirectionalContext context, BufferedImage image, Map<Integer, Block> palette,
			int offsetRX, int offsetRY, int offsetRZ) {
		
		this(context, image, palette, offsetRX, offsetRY, offsetRZ, 0, 0, image.getWidth(), image.getHeight());
	}
	
	public ImageStructure(DirectionalContext context, BufferedImage image,
			int offsetRX, int offsetRY, int offsetRZ, int startX, int startY, int endX, int endY) {
		
		this(context, image,
				generatePalette(context, image, startX, startY, endX, endY),
				offsetRX, offsetRY, offsetRZ, startX, startY, endX, endY);
	}
	
	public ImageStructure(DirectionalContext context, BufferedImage image,
			int offsetRX, int offsetRY, int offsetRZ, int startX, int startY, int endX, int endY, int multRX, int multRY) {
		
		this(context, image,
				generatePalette(context, image, startX, startY, endX, endY),
				offsetRX, offsetRY, offsetRZ, startX, startY, endX, endY, multRX, multRY);
	}
	
	public ImageStructure(DirectionalContext context, BufferedImage image, Map<Integer, Block> blockPalette,
			int offsetRX, int offsetRY, int offsetRZ, int startX, int startY, int endX, int endY) {
		
		this(context, image, blockPalette, offsetRX, offsetRY, offsetRZ, startX, startY, endX, endY, 1, 1);
	}
	
	public ImageStructure(DirectionalContext context, BufferedImage image, Map<Integer, Block> blockPalette,
			int offsetRX, int offsetRY, int offsetRZ, int startX, int startY, int endX, int endY, int multRX, int multRY) {
		
		this(context, image, blockPalette, blockPalette, offsetRX, offsetRY, offsetRZ, startX, startY, endX, endY, multRX, multRY);
	}
	
	public ImageStructure(DirectionalContext context, BufferedImage image, Map<Integer, Block> blockPalette, Map<Integer, Block> lowerBlockPalette,
			int offsetRX, int offsetRY, int offsetRZ, int startX, int startY, int endX, int endY, int multRX, int multRY) {
		
		assert startX >= 0 && startX <= image.getWidth();
		assert startY >= 0 && startY <= image.getHeight();
		assert endX >= 0 && endX <= image.getWidth();
		assert endY >= 0 && endY <= image.getHeight();
		assert startX <= endX;
		assert startY <= endY;
		
		this.offsetRX = offsetRX;
		this.offsetRY = offsetRY;
		this.offsetRZ = offsetRZ;
		this.context = context;
		
		
		final int
				width = endX - startX,
				height = endY - startY,
				size = width * height;
		
		this.minRenderDistance = (endX + offsetRX + (CHUNK_WIDTH - 1)) / CHUNK_WIDTH;
		
		
		Map<Integer, String> palette = paletteFromBlockPalette(blockPalette);
		Map<Integer, String> lowerPalette = lowerBlockPalette == blockPalette ? palette : paletteFromBlockPalette(lowerBlockPalette);
		
		
		Timer timer = Timer.startNewTimer();
		
		if(size <= MAX_REGION_SIZE) {
			
			regions = List.of(new Region(context, image, palette, lowerPalette, startX, startY, endX, endY, multRX, multRY));
			
		} else if(height <= 256 && height < width) {
			
			int regionWidth = MAX_REGION_SIZE / height;
			
			regions = new ArrayList<>((width + regionWidth - 1) / regionWidth);
			
			for(int x = startX; x < endX; ) {
				regions.add(new Region(context, image, palette, lowerPalette, x, startY, x = Math.min(x + regionWidth, endX), endY, multRX, multRY));
			}
			
		} else if(width <= 256 && width < height) {
			
			int regionHeight = MAX_REGION_SIZE / width;
			
			regions = new ArrayList<>((height + regionHeight - 1) / regionHeight);
			
			for(int y = startY; y < endY; ) {
				regions.add(new Region(context, image, palette, lowerPalette, startX, y, endX, y = Math.min(y + regionHeight, endY), multRX, multRY));
			}
			
		} else {
			
			regions = new ArrayList<>((size + (MAX_REGION_SIZE - 1)) / MAX_REGION_SIZE);
			
			int regionWidth, regionHeight;
			if(width < height) {
				regionWidth = 128;
				regionHeight = 256;
			} else {
				regionWidth = 256;
				regionHeight = 128;
			}
			
			for(int x = startX, nextX = regionWidth; x < endX; x = nextX, nextX = Math.min(x + regionWidth, endX)) {
				for(int y = startY, nextY = regionHeight; y < endY; y = nextY, nextY = Math.min(y + regionHeight, endY)) {
					regions.add(new Region(context, image, palette, lowerPalette, x, y, nextX, nextY, multRX, multRY));
				}
			}
		}
		
		timer.logElapsed("Regions");
	}
	
	
	@Override
	public int minRenderDistance() {
		return minRenderDistance;
	}
	
	
	@Override
	public void writeTo(OutputStreamWriter out) throws IOException {
		for(Region region : regions)
			region.writeTo(out);
	}
	
	
	/** Описывает область структуры, обычно 256x128 блоков (или 256x128).
	 * Общая площадь не больше {@link ImageStructure#MAX_REGION_SIZE} */
	public class Region {
		
		/** До этого порога одинаковые блоки подряд размещаются
		 * через команду setblock, дальше - через fill */
		private static final int FILL_COMMAND_THRESHOLD = 1;
		private final int startX, startY, endX, endY, startRY, endRY, multRX, multRY;
		private final String[][] data;
		
		private static void setDataBlock(BufferedImage image, Map<Integer, String> palette, String[][] data, int x, int y, int imgX, int imgY) {
			data[x][y] = palette.get(image.getRGB(imgX, imgY) & 0xFFFFFF);
			assert data[x][y] != null : x + " " + y + "; color: " + Integer.toHexString(image.getRGB(imgX, imgY) & 0xFFFFFF);
		}
		
		public Region(Context context, BufferedImage image, Map<Integer, String> palette, Map<Integer, String> lowerPalette, int startX, int startY, int endX, int endY, int multRX, int multRY) {
			this.startX = startX;
			this.startY = startY;
			this.endX = endX;
			this.endY = endY;
			this.multRX = multRX;
			this.multRY = multRY;
			
			var width  = endX - startX;
			var height = endY - startY;
			
			this.startRY = image.getHeight() - startY + offsetRY;
			this.endRY = startRY - height;
			
			System.out.println("Created region from " + startX + ", " + startY + ", size " + width + "x" + height);
			
			// Локальные переменные для кэширования полей
			var data = this.data = new String[width][height + 1]; // Один дополнительный элемент, который будет равен null
			
			int heightM1 = height - 1;
			
			for(int x = 0, imgX = startX; x < width; x++, imgX++) {
				for(int y = 0, imgY = startY; y < heightM1; y++, imgY++) {
					setDataBlock(image, palette, data, x, y, imgX, imgY);
				}
			}
			
			for(int x = 0, imgX = startX, lastY = startY + heightM1; x < width; x++, imgX++) {
				setDataBlock(image, lowerPalette, data, x, heightM1, imgX, lastY);
			}
		}
		
		public void writeTo(OutputStreamWriter out) throws IOException {
			var width  = endX - startX;
			var height = endY - startY;
			var data   = this.data;
			
			Map<String, Integer> frequencies = new HashMap<>();
			
			for(int x = 0; x < width; x++) {
				var row = data[x];
				
				for(int y = 0; y < height; y++) {
					String id = row[y];
					frequencies.put(id, frequencies.getOrDefault(id, 0) + 1);
				}
			}
			
			var offsetRZ = ImageStructure.this.offsetRZ;
			var startRX  = startX + offsetRX;
			var endRX    = endX   + offsetRX;
			var multRX   = this.multRX;
			var multRY   = this.multRY;
			
			var startRY_M1 = startRY - 1;
			
			var direction = context.getDirection();
			
			String mostFrequentId = frequencies.entrySet().stream().max((entry1, entry2) -> entry1.getValue() - entry2.getValue()).get().getKey();
			out.write("fill ");
			out.write(direction.getCoords((startRX + 1) * multRX, startRY_M1 * multRY, offsetRZ));
			out.write(' ');
			out.write(direction.getCoords(        endRX * multRX,      endRY * multRY, offsetRZ));
			out.write(' ');
			out.write(mostFrequentId);
			out.write('\n');
			
			// x, y - координаты пикселя в изображении
			// rx, ry - координаты блока в майнкрафте
			for(int x = 0, rx = startRX + 1; x < width; x++, rx++) {
				int fillStartRY = 0;
				String fillId = null;
				
				var row = data[x];
				
				for(int y = 0, ry = startRY_M1; y <= height; y++, ry--) {
					String id = row[y];
					
					assert id != null || y == height : "x: " + x + " of " + width + "; y: " + y + " of " + height;
					
					if(id != fillId) {
						
						if(fillId != null) {
							
							int mrx = rx * multRX;
							
							if(fillStartRY - ry <= FILL_COMMAND_THRESHOLD) {
								for(int fillRY = fillStartRY; fillRY > ry; fillRY--) {
									out.write("setblock ");
									out.write(direction.getCoords(mrx, fillRY * multRY, offsetRZ));
									out.write(' ');
									out.write(fillId);
									out.write('\n');
								}
								
							} else {
								out.write("fill ");
								out.write(direction.getCoords(mrx, fillStartRY * multRY, offsetRZ));
								out.write(' ');
								out.write(direction.getCoords(mrx,    (ry + 1) * multRY, offsetRZ));
								out.write(' ');
								out.write(fillId);
								out.write('\n');
							}
						}
						
						fillId = id == mostFrequentId ? null : id;
						fillStartRY = ry;
					}
				}
			}
		}
	}
}