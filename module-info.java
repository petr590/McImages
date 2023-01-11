module mcimages {
	requires java.base;
	requires transitive java.desktop;
	requires x590.util;
	requires transitive x590.argparser;
	
	exports mcimages;
	exports mcimages.block;
	exports mcimages.color;
	exports mcimages.context;
	exports mcimages.structure;
}