package mcimages.structure;

import java.io.IOException;
import java.io.OutputStreamWriter;

/** Описывает структуру */
public abstract class Structure {
	
	public abstract int minRenderDistance();
	
	public abstract void writeTo(OutputStreamWriter out) throws IOException;
}